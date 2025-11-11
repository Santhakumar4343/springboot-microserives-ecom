package com.consumer.resttemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class ConsumerController {

   private final RestTemplate restTemplate;

    @GetMapping("/server-info")
    public String getServerInfo() {
        return restTemplate.getForObject("http://localhost:8087/server-info", String.class);
    }
}
