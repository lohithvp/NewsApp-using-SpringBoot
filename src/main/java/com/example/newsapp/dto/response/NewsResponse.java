package com.example.newsapp.dto.response;

import com.example.newsapp.model.News;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    private String source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlImg;
    private String publishedAt;


    @JsonProperty("source")
    public void setSource(JsonNode sourceNode) {
        if (sourceNode != null && sourceNode.has("name")) {
            this.source = sourceNode.get("name").asText();
        }
    }

    public NewsResponse(News news) {
        this.id = news.getId();
        this.source = news.getSource();
        this.author = news.getAuthor();
        this.title = news.getTitle();
        this.description = news.getDescription();
        this.url = news.getUrl();
        this.urlImg = news.getUrl();
        this.publishedAt = news.getPublishedAt();
    }
}
