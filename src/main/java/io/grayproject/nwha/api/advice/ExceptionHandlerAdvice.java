package io.grayproject.nwha.api.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleConflict(Exception exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
