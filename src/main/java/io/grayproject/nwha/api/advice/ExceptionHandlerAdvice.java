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
        String message = "Developer suffer when you try to make requests that break the server. These requests can cause errors, crashes, and other problems that waste time and resources. Please stop making these requests, or the dogs and foxes will come for you.";
        objectNode.put("message", message);
        return ResponseEntity.badRequest().body(objectNode);
    }
}