package ru.leppla.RequestAnalytics.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
        logger.error("RuntimeException caught: {}", ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        logger.warn("IllegalArgumentException: {}", ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), ex, request);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalState(IllegalStateException ex, WebRequest request) {
        logger.error("Illegal state: {}", ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage(), ex, request);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointer(NullPointerException ex, WebRequest request) {
        logger.error("Null pointer exception: {}", ex.getMessage(), ex);
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex, request);
    }

    private ResponseEntity<Object> buildResponseEntity(
            HttpStatus status, String message, Exception ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("path", request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(body, status);
    }
}
