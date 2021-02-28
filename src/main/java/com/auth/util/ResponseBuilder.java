package com.auth.util;

import com.auth.payload.Response;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@Component
public class ResponseBuilder<T> {
    public BiFunction<T, List<T>, Response> successResponse = (obj, objList) -> {
        Response response = new Response();

        response.setDataList(objList);
        response.setData(obj);
        response.setMessage("Success");
        response.setStatusCode(200);
        return response;
    };
}
