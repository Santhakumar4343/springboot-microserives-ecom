package com.consumer.restclient;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequiredArgsConstructor
public class RestClientController {
  private final RestClientConsumer restClientConsumer;
    @GetMapping("/rest-client")
    public String getInstanceInfo(){
       return restClientConsumer.getInstanceInfo();
    }
}
