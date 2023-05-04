package io.grayproject.nwha.api.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = IOException.class)
    public String handleIOException(IOException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ObjectNode> handleCommonConflict(Exception exception) {
        log.error(exception.getMessage());
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("message", exception.getMessage());
        return ResponseEntity.badRequest().body(objectNode);
    }
}