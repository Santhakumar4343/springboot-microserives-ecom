package com.consumer.feignclient;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeignController {

    private final ProviderFeignClient providerFeignClient;
    @GetMapping("/feign-info")
    public String getServerInfo(){
        return providerFeignClient.getServerInfo();
    }
}
