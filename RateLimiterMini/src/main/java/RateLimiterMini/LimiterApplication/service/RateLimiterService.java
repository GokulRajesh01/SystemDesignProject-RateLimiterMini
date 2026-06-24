package RateLimiterMini.LimiterApplication.service;

import RateLimiterMini.LimiterApplication.exception.RequestLimitExceededException;
import RateLimiterMini.LimiterApplication.store.RateLimiterStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Deque;

@Service
public class RateLimiterService {
    private final RateLimiterStore store;

    private final NaturalNumberService numberService;

    public RateLimiterService(RateLimiterStore store, NaturalNumberService numberService){
        this.store = store;
        this.numberService = numberService;
    }

    // Sliding Window Algorithm
    public Deque<Instant> enforceSlidingWindow(int windowCount, Deque<Instant> history){
        Instant currentTime = Instant.now();
        history.removeIf(time -> time.isBefore(currentTime.minus(1, ChronoUnit.HOURS)));
        if(history.size() >= windowCount){
            throw new RequestLimitExceededException("Number of requests allowed has been exceeded for this API. Please try again in a while");
        }
        history.addLast(currentTime);
        return history;
    }

    public void enforceRateLimit(String userId){
        // Retrieve the User's history first
        Deque<Instant> newStoreData = enforceSlidingWindow(10, store.getUserHistory(userId));
        store.updateUserHistory(userId, newStoreData);
        numberService.printfirst10naturalnumbers();
    }
}
