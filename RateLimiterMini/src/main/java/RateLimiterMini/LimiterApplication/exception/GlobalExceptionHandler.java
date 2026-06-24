package RateLimiterMini.LimiterApplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RequestLimitExceededException.class)
    public ResponseEntity<String> handleRequestLimitExceededException(Exception exception){
        return ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .body(exception.getMessage());
    }
}
