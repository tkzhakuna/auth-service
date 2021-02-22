package com.auth.payload;

import lombok.Data;

@Data
public class Response {
    private int statusCode;
    private String message;
    private Object data;


}
