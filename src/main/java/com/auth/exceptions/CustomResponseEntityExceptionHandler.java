package com.auth.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  
	@ExceptionHandler
    public final ResponseEntity<Object> handleOtherException(Exception ex, WebRequest request){
        Response exceptionResponse = new Response(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
	
    @ExceptionHandler
    public final ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request){
        Response exceptionResponse = new Response(HttpStatus.NOT_FOUND.value(),ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    public final ResponseEntity<Object> handleAlreadyExists(AlreadyExistsException ex, WebRequest request){
        Response exceptionResponse = new Response(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleNotSaved(NotSavedException ex, WebRequest request){
        Response exceptionResponse = new Response(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler
    public final ResponseEntity<Object> handleNotDeleted(NotDeletedException ex, WebRequest request){
        Response exceptionResponse = new Response(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleUserNameAlredyExists(UsernameAlreadyExistsException ex, WebRequest request){
        Response exceptionResponse = new Response(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleUnexpectedException(UnexpectedException ex, WebRequest request){
        Response exceptionResponse = new Response(HttpStatus.EXPECTATION_FAILED.value(),ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleInvalidParameterException(InvalidParameterException ex, WebRequest request){
        Response exceptionResponse = new Response(HttpStatus.EXPECTATION_FAILED.value(),ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.EXPECTATION_FAILED);
    }

    
}
