package RateLimiterMini.LimiterApplication.controller;

import RateLimiterMini.LimiterApplication.service.NaturalNumberService;
import RateLimiterMini.LimiterApplication.service.RateLimiterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimiterController {
    private final RateLimiterService service;

    public RateLimiterController(RateLimiterService service, NaturalNumberService naturalNumberService){
        this.service = service;
    }

    @GetMapping("/dummy-endpoint")
    public void printFirst10NaturalNumbers(@RequestParam String userId){
        service.enforceRateLimit(userId);
    }
}
