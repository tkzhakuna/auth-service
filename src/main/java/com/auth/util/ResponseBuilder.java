package com.auth.util;

import com.auth.payload.Response;
import org.springframework.stereotype.Component;

import java.util.function.Function;
@Component
public class ResponseBuilder<T> {
    public  Function<T, Response>successResponse=(obj)->{
        Response response=new Response();
        response.setData(obj);
        response.setMessage("Success");
        response.setStatusCode(200);
        return response;
    };
}
