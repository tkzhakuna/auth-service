package com.auth.payload;

import lombok.Data;

import java.util.List;

@Data
public class Response {
    private int statusCode;
    private String message;
    private Object data;
    private List<?> dataList;
    }
