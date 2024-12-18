package com.example.newsapp.controller;

import com.example.newsapp.dto.response.NewsListResponse;
import com.example.newsapp.dto.response.ResponseDTO;
import com.example.newsapp.exception.EmptyException;
import com.example.newsapp.exception.NotFoundException;
import com.example.newsapp.services.NewsServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class NewsController {

    @Autowired
    private NewsServices newsServices;

    @GetMapping("/v1/news/search/{pageNo}")
    public ResponseEntity<ResponseDTO<NewsListResponse>> getNews(@PathVariable int pageNo, @RequestParam String topic) throws JsonProcessingException, EmptyException {
        return newsServices.getNewsBySearch(pageNo, topic);
    }

    @GetMapping("/v1/news/{pageNo}")
    public ResponseEntity<ResponseDTO<NewsListResponse>> getNewsFromDB(@PathVariable long pageNo) throws NotFoundException,EmptyException {
        return newsServices.getNewsByDb(pageNo);
    }


}
