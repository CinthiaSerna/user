package com.example.user.helper;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ResponseBuild {
    public Response success(){
        return Response.builder().code(HttpStatus.OK.value()).build();
    }
    public Response success(Object data){
        return Response.builder().code(HttpStatus.OK.value()).data(data).build();
    }
    public Response failed(){
        return Response.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
    }
    public Response failed(Object data){
        return Response.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).data(data).build();
    }
}
