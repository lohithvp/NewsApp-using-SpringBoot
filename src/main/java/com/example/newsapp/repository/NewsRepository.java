package com.example.newsapp.repository;

import com.example.newsapp.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface NewsRepository extends JpaRepository<News, Long> {

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE news AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}
