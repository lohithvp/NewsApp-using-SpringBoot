package com.example.newsapp.model;

import com.example.newsapp.dto.response.NewsResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String source;
    private String author;
    private String title;

    @Column(name = "description", length = 4096)
    private String description;

    @Column(name = "url", length = 2048)
    private String url;

    private String urlImg;
    private String publishedAt;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public News(NewsResponse newsResponse){
        this.source = newsResponse.getSource();
        this.author = newsResponse.getAuthor();
        this.title = newsResponse.getTitle();
        this.description = newsResponse.getDescription();
        this.url = newsResponse.getUrl();
        this.urlImg = newsResponse.getUrlImg();
        this.publishedAt = newsResponse.getPublishedAt();
    }

}
