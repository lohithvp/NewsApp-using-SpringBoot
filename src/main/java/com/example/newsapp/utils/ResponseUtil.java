package com.example.newsapp.utils;

import com.example.newsapp.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseUtil {

    public <T> ResponseEntity<ResponseDTO<T>> successResponse(T responseData) {
        return ResponseEntity.ok(new ResponseDTO<>(0, 200, "Success", responseData));
    }

    public <T> ResponseEntity<ResponseDTO<T>> successResponse(T responseData, String message) {
        return ResponseEntity.ok(new ResponseDTO<>(0, 200, message, responseData));
    }

    public ResponseEntity<ResponseDTO<?>> errorResponse() {
        return ResponseEntity.ok(new ResponseDTO<>(-1, 404, "Failed", null));
    }

    public ResponseEntity<ResponseDTO<?>> errorResponse(int code, String message) {
        return ResponseEntity.ok(new ResponseDTO<>(-1, 404, message, null));
    }
}
