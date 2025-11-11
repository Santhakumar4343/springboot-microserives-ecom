package com.producer.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class ProducerController {
    @Value("${server.port}")
    private String port;
    @GetMapping("/server-info")
    public String getInstanceInfo(){
        return "Server running on port :"+port;
    }
}
