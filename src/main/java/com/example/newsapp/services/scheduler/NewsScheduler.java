package com.example.newsapp.services.scheduler;

import com.example.newsapp.services.NewsServices;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@Log4j2
public class NewsScheduler {

    @Autowired
    private NewsServices newsService;

    public NewsScheduler() {
        log.info("NewsScheduler constructor called");
    }

    @Scheduled(cron = "0 0 */6 * * *")
    @Transactional
    public void scheduleNewsUpdate() throws Exception {
        log.info("Data is Updated at {}", LocalDateTime.now());
        newsService.updateNewsDatabase();
    }

    @PostConstruct
    public void initializeNewsData() throws Exception {
        log.info("Data is fetched at Startup");
        newsService.updateNewsDatabase();
    }
}

