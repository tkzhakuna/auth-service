package com.auth.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.format.DateTimeParseException;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  
	@ExceptionHandler
    public final ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value());
        apiError.setMessage(ex.getMessage());
        apiError.setDebugMessage(ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }
    @ExceptionHandler
    public final ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, WebRequest request){
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED.value());
        apiError.setMessage("Invalid Login");
        apiError.setDebugMessage(ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request){
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value());
        apiError.setMessage(ex.getMessage());
        apiError.setDebugMessage(ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }


    @ExceptionHandler
    public final ResponseEntity<Object> handleAlreadyExists(AlreadyExistsException ex, WebRequest request){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value());
        apiError.setMessage(ex.getMessage());
        apiError.setDebugMessage(ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleNotSaved(NotSavedException ex, WebRequest request){
        ApiError apiError = new ApiError(HttpStatus.EXPECTATION_FAILED.value());
        apiError.setMessage(ex.getMessage());
        apiError.setDebugMessage(ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }
    
    @ExceptionHandler
    public final ResponseEntity<Object> handleNotDeleted(NotDeletedException ex, WebRequest request){
        ApiError apiError = new ApiError(HttpStatus.EXPECTATION_FAILED.value());
        apiError.setMessage(ex.getMessage());
        apiError.setDebugMessage(ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleUserNameAlredyExists(UsernameAlreadyExistsException ex, WebRequest request){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value());
        apiError.setMessage(ex.getMessage());
        apiError.setDebugMessage(ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleUnexpectedException(UnexpectedException ex, WebRequest request){
        ApiError apiError = new ApiError(HttpStatus.EXPECTATION_FAILED.value());
        apiError.setMessage(ex.getMessage());
        apiError.setDebugMessage(ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleInvalidParameterException(InvalidParameterException ex, WebRequest request){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value());
        apiError.setMessage(ex.getMessage());
        apiError.setDebugMessage(ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException(
            RuntimeException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value());
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(UnexpectedErrorException.class)
    protected ResponseEntity<Object> handleUnexpectedError(
            UnexpectedErrorException ex) {
        ApiError apiError = new ApiError(HttpStatus.EXPECTATION_FAILED.value());
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(DateTimeParseException.class)
    protected ResponseEntity<Object> handleDateTimeParseException(
            DateTimeParseException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value());
        apiError.setMessage(ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }


    @ExceptionHandler(UniqueViolationException.class)
    protected ResponseEntity<Object> handleUniqueViolationException(
            UniqueViolationException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value());
        apiError.setMessage(ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            EntityNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value());
        apiError.setMessage(ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }



    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handleValidationException(
            ValidationException ex) {

        ApiValidationError apiVError = new ApiValidationError();
        apiVError.setMessage(ex.getMessage());
        apiVError.setObject("object");
        apiVError.setField("field");
        apiVError.setRejectedValue("RejValue");
        return buildValidationResponseEntity(apiVError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.getStatus()));
    }

    private ResponseEntity<Object> buildValidationResponseEntity(ApiValidationError apiVError) {
        return new ResponseEntity<>(apiVError.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
}
