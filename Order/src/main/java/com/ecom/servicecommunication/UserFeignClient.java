package com.ecom.servicecommunication;

import com.ecom.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER")
public interface UserFeignClient {
    @GetMapping("/user/v1/getUser/{id}")
    UserResponse getUser(@PathVariable Long id);
}
