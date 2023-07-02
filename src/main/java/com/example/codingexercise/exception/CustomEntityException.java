package com.example.codingexercise.exception;

import com.example.codingexercise.response.ServerErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {



    @ExceptionHandler
    public final ResponseEntity<Object> duplicateRecordExceptionHandler(DuplicateException ex, WebRequest request){
        ServerErrorResponse exceptionResponse=  new ServerErrorResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> badRequestExceptionHandler(BadRequestException ex, WebRequest request){
        ServerErrorResponse exceptionResponse=  new ServerErrorResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }


}