package com.example.newsapp.utils;

import com.example.newsapp.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class WebClientUtil {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public String externalGetRequest(String url) {
        return webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> {
                            return Mono.error(new NotFoundException("Client Api error :NOTFOUND"));
                        })
                .bodyToMono(String.class)
                .block();
    }
}
