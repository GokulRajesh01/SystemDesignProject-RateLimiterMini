package RateLimiterMini.LimiterApplication.service;

import RateLimiterMini.LimiterApplication.exception.RequestLimitExceededException;
import RateLimiterMini.LimiterApplication.store.RateLimiterStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RateLimiterServiceTest {

    @Mock
    private RateLimiterStore store;

    @Mock
    private NaturalNumberService numberService;

    @InjectMocks
    private RateLimiterService service;

    @Test
    void shouldAllowRequestWhenUserHasLessThanTenRequests() {
        // Arrange
        Deque<Instant> history = new ArrayDeque<>();
        Instant now = Instant.now();
        for(int i = 0; i < 9; i++){
            history.addLast(now.minusSeconds(i * 60));
        }
        when(store.getUserHistory("user1"))
                .thenReturn(history);
        // Act
        service.enforceRateLimit("user1");
        // Assert
        verify(store).updateUserHistory(
                org.mockito.ArgumentMatchers.eq("user1"),
                org.mockito.ArgumentMatchers.any(Deque.class)
        );
    }

    @Test
    void shouldThrowExceptionWhenUserHasTenActiveRequests() {
        // Arrange
        Deque<Instant> history = new ArrayDeque<>();
        Instant now = Instant.now();
        for(int i = 0; i < 10; i++){
            history.addLast(now.minusSeconds(i * 60));
        }
        when(store.getUserHistory("user1"))
                .thenReturn(history);
        // Act + Assert
        assertThrows(
                RequestLimitExceededException.class,
                () -> service.enforceRateLimit("user1")
        );
    }
}