package com.consumer.restclient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class RestClientConsumer {
    private final RestClient restClient;

    public String getInstanceInfo(){
       return restClient.get().uri("http://localhost:8087/server-info").retrieve().body(String.class);
    }
}
