package com.example.newsapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsListResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<NewsResponse> newsList = new ArrayList<>();

    public void addNews(NewsResponse oneNews) {
        newsList.add(oneNews);
    }

    public void addNewsList(List<NewsResponse> newsListCollection) {
        newsList.addAll(newsListCollection);
    }
}
