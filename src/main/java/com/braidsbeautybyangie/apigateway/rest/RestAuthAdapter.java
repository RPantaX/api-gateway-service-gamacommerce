package com.braidsbeautybyangie.apigateway.rest;

import com.braidsbeautybyangie.apigateway.dto.TokenValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface RestAuthAdapter {
    @GetMapping("/v1/user-service/user/auth/validate")
    TokenValidationResponse validateToken(@RequestParam("token") String token);
}
