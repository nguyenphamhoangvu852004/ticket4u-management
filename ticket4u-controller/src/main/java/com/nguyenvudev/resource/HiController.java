package com.nguyenvudev.resource;


import com.nguyenvudev.service.event.EventApplicationService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;

@RestController
@RequestMapping
public class HiController {
    private final EventApplicationService eventApplicationService;
    private final SecureRandom secureRandom;
    public HiController(EventApplicationService eventApplicationService) {
        this.eventApplicationService = eventApplicationService;
        this.secureRandom = new SecureRandom();
    }

    @GetMapping("/ping1")
    @RateLimiter(name = "backendA",fallbackMethod = "fallbackPong")
    public String pong(){
        return this.eventApplicationService.sayHi("HiController");
    }

    public String fallbackPong(Throwable throwable){
        return "Too many request";
    }

    @GetMapping("/ping2")
    @RateLimiter(name = "backendB", fallbackMethod = "fallbackHuhu")
    public String huhu(){
        return this.eventApplicationService.sayHi("HOHOHOO");
    }

    public String fallbackHuhu(Throwable throwable){
        return "sadlkjfl;asdkjf;lkdsa";
    }


    @GetMapping("/circuit-breaker")
    @CircuitBreaker(name = "checkRandom",fallbackMethod = "fallbackCircuitBreaker")
    public String circuitBeaker() {
        int productId = this.secureRandom.nextInt(20) + 1;
        String url = "https://fakestoreapi.com/products/" + productId;
        return new RestTemplate().getForObject(url,String.class);
    }
    public String fallbackCircuitBreaker(Throwable throwable){
//        return throwable.getMessage();
        return "Service fake store api error";
    }
}
