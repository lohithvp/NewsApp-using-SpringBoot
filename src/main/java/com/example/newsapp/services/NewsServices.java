package com.example.newsapp.services;

import com.example.newsapp.dto.response.NewsListResponse;
import com.example.newsapp.dto.response.ResponseDTO;
import com.example.newsapp.exception.EmptyException;
import com.example.newsapp.exception.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface NewsServices {

    ResponseEntity<ResponseDTO<NewsListResponse>> getNewsBySearch(int pageNo, String topic) throws JsonProcessingException, EmptyException;

    void updateNewsDatabase() throws JsonProcessingException;

    ResponseEntity<ResponseDTO<NewsListResponse>> getNewsByDb(long pageNo) throws NotFoundException, EmptyException;

    NewsListResponse fetchNewsListResponseAndCache(long pageNo) throws NotFoundException;
}
