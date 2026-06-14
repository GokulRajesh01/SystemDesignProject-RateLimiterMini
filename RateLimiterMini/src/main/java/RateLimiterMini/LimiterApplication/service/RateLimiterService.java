package RateLimiterMini.LimiterApplication.service;

import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {
    public void printfirst10naturalnumbers(){
        int num = 0;
        while(num++ < 10){
            System.out.println(num);
        }
    }
}
