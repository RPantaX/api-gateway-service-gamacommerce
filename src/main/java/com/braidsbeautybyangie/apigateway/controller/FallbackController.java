package com.braidsbeautybyangie.apigateway.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {
    @GetMapping("/orderServiceFallback")
    public String orderServiceFallbackMethod() {
        return "Order Service is taking too long to respond or is down. Please try again later";
    }
    @GetMapping("/productServiceFallback")
    public String productServiceFallbackMethod() {
        return "Product Service is taking too long to respond or is down. Please try again later";
    }
    @GetMapping("/paymentServiceFallback")
    public String paymentServiceFallbackMethod() {
        return "Payment Service is taking too long to respond or is down. Please try again later";
    }
    @GetMapping("/reservationServiceFallback")
    public String reservationServiceFallbackMethod() {
        return "Reservation Service is taking too long to respond or is down. Please try again later";
    }
    @GetMapping("/creditCardProcessorServiceFallback")
    public String creditCardServiceFallbackMethod() {
        return "Credit Card Service is taking too long to respond or is down. Please try again later";
    }
}
