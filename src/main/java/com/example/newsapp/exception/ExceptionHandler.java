package com.example.newsapp.exception;

import com.example.newsapp.dto.response.ResponseDTO;
import com.example.newsapp.utils.ResponseUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Log4j2
public class ExceptionHandler {

    @Autowired
    ResponseUtil responseUtil;

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ResponseDTO<?>> handleNotFoundException(Exception exception) {
        log.error(exception.getMessage());
        return responseUtil.errorResponse(404, exception.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {EmptyException.class})
    public ResponseEntity<ResponseDTO<?>> handleEmptyException(Exception exception) {
        log.error(exception.getMessage());
        return responseUtil.errorResponse(500, exception.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {JsonProcessingException.class})
    public ResponseEntity<ResponseDTO<?>> handleObjectMapperException(Exception exception) {
        log.error(exception.getMessage());
        return responseUtil.errorResponse(500, "Error related to Object Mapper :" + exception.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ResponseDTO<?>> handleUnhandledException(Exception exception) {
        Throwable cause = exception.getCause();
        log.error(exception.getMessage());
        if (cause instanceof NotFoundException notFoundException) {
            return responseUtil.errorResponse(HttpStatus.NOT_FOUND.value(), notFoundException.getMessage());
        }

        return responseUtil.errorResponse(500, exception.getMessage());
    }
}
