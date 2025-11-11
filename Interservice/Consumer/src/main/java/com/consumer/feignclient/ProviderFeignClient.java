package com.consumer.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "Provider",url = "http://localhost:8087")
public interface ProviderFeignClient {
    @GetMapping("/server-info")
    String getServerInfo();
}
