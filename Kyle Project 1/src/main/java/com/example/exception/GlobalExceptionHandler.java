package com.example.exception;

import com.example.response.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
@RestController
@Log4j2
public class GlobalExceptionHandler {

    @Order(4)
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity handleException(Exception e) {
        log.warn(e);
        e.printStackTrace();
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity(new ErrorResponse("something went wrong"), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @Order(1)
    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ResponseEntity handleHttpMediaTypeNotSupportedException(Exception e) {
        log.warn(e);
        return new ResponseEntity(new ErrorResponse("media type is wrong"), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @Order(2)
    @ExceptionHandler(value = NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleNoResourceFoundException(NoResourceFoundException e) {
        log.warn(e);
        return new ResponseEntity(new ErrorResponse("url source is wrong"), HttpStatus.BAD_REQUEST);
    }

    @Order(3)
    @ExceptionHandler(value = ResponseStatusException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public ResponseEntity handleNoResourceFoundException(ResponseStatusException e) {
        log.warn(e);
        return new ResponseEntity(new ErrorResponse("Too many request"), HttpStatus.TOO_MANY_REQUESTS);
    }

}
