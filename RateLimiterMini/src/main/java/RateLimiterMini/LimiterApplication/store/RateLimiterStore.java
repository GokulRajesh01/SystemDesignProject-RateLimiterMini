    package RateLimiterMini.LimiterApplication.store;


    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Component;

    import java.time.Instant;
    import java.util.ArrayDeque;
    import java.util.Deque;
    import java.util.HashMap;
    import java.util.Map;

    @Slf4j
    @Component
    public class RateLimiterStore {
        private final Map<String, Deque<Instant>> store;

        public RateLimiterStore(){
            this.store = new HashMap<String, Deque<Instant>>();
        }

        public Deque<Instant> getUserHistory(String userId){
            // userId is the key to be searched
            if(store.containsKey(userId)){
                return store.get(userId);
            }
            return new ArrayDeque<Instant>();
        }

        public void updateUserHistory(String userId, Deque<Instant> timestamps){
            // check if there are less than 10 entries in the deque for timestamps
            if(!store.containsKey(userId)){
                store.put(userId, timestamps);
                log.info("New user added to the store with the provided timestamps.");
            }
            else if(store.replace(userId, store.get(userId), timestamps)){
                log.info("Store updated successfully.");
            }
        }
    }
