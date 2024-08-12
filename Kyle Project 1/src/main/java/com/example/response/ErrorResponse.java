package com.example.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    String error;

    public static ErrorResponse error(String error) {
        return new ErrorResponse(error);
    }
}
