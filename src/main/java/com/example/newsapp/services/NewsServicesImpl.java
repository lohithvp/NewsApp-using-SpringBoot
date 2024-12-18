package com.example.newsapp.services;

import com.example.newsapp.dto.response.NewsListResponse;
import com.example.newsapp.dto.response.NewsResponse;
import com.example.newsapp.dto.response.ResponseDTO;
import com.example.newsapp.exception.EmptyException;
import com.example.newsapp.exception.NotFoundException;
import com.example.newsapp.model.News;
import com.example.newsapp.repository.NewsRepository;
import com.example.newsapp.utils.NewApiClientUtils;
import com.example.newsapp.utils.ResponseUtil;
import com.example.newsapp.utils.WebClientUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Log4j2
public class NewsServicesImpl implements NewsServices {

    @Value("${error.news.empty}")
    private String newsListEmptyMessage;

    @Value("${error.news.notFound}")
    private String newsListNotFoundMessage;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private WebClientUtil webClientUtil;

    @Value("${articlePerPage}")
    int articlePerPage;

    @Value("${url.db}")
    String urlForDb;

    @Value("${url.search}")
    String urlForSearch;


    @Override
    public ResponseEntity<ResponseDTO<NewsListResponse>> getNewsBySearch(int pageNo, String topic) throws JsonProcessingException, EmptyException {

        String responseJson = webClientUtil.externalGetRequest(String.format(urlForSearch, topic));

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode articlesNode = NewApiClientUtils.getArticlesJson(responseJson);

        int startIndex = (pageNo - 1) * articlePerPage;
        int endIndex = Math.min(startIndex + articlePerPage, articlesNode.size());

        NewsListResponse newsListResponse = new NewsListResponse();
        for (int i = startIndex; i < endIndex; i++) {
            JsonNode article = articlesNode.get(i);
            newsListResponse.addNews(objectMapper.readValue(article.toString(), NewsResponse.class));
        }
        if (newsListResponse.getNewsList().isEmpty()) {
            throw new EmptyException(newsListEmptyMessage);
        }
        return responseUtil.successResponse(newsListResponse);
    }


    @Override
    @CacheEvict(value = "news", allEntries = true)
    public void updateNewsDatabase() throws JsonProcessingException {
        String responseJson = webClientUtil.externalGetRequest(String.format(urlForDb));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode articlesNode = NewApiClientUtils.getArticlesJson(responseJson);
        newsRepository.deleteAll();
        newsRepository.resetAutoIncrement();

        for (JsonNode article : articlesNode) {
            NewsResponse newsResponse = objectMapper.readValue(article.toString(), NewsResponse.class);
            newsRepository.save(new News(newsResponse));
        }
        log.info("Successfully saved the data in db");
    }

    @Override
    public ResponseEntity<ResponseDTO<NewsListResponse>> getNewsByDb(long pageNo) throws NotFoundException, EmptyException {
        log.info("Requesting news for pageNo: {}", pageNo);
        NewsListResponse cachedNewsListResponse = fetchNewsListResponseAndCache(pageNo);
        if(cachedNewsListResponse.getNewsList().isEmpty()) throw new EmptyException(newsListEmptyMessage);
        return responseUtil.successResponse(cachedNewsListResponse);
    }

    @Cacheable(value = "news", key = "#pageNo")
    @Override
    public NewsListResponse fetchNewsListResponseAndCache(long pageNo) throws NotFoundException {

        long startIndex = (pageNo - 1) * articlePerPage;
        long sizeOfNewsArticles = newsRepository.count();
        long endIndex = Math.min(startIndex + articlePerPage, sizeOfNewsArticles);

        NewsListResponse newsListResponse = new NewsListResponse();
        for (long i = startIndex + 1; i <= endIndex; i++) {
            Optional<News> optionalNews = Optional.ofNullable(newsRepository.findById(i)
                    .orElseThrow(() -> new NotFoundException(newsListNotFoundMessage)));
            newsListResponse.addNews(new NewsResponse(optionalNews.get()));
        }
        log.info("Successfully fetched data from dB");
        return newsListResponse;
    }
}
