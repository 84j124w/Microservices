package com.eazybank.exception;

import com.eazybank.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                    HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> validationErrors = new Hashtable<>();
        List<ObjectError> validdationErrorList = ex.getBindingResult().getAllErrors();

        validdationErrorList.forEach((error) ->{
            String fieldName = ((FieldError ) error).getField();
            String validationMsg = error.getDefaultMessage();
            validationErrors.put(fieldName, validationMsg);
        });

        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception, WebRequest request){
        ErrorResponseDto errorResponseDto =
                new ErrorResponseDto(
                        request.getDescription(false),
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        exception.getMessage(),
                        LocalDateTime.now()
                );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CardAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> cardAlreadyExistsException(CardAlreadyExistsException exception, WebRequest request){
        ErrorResponseDto errorResponseDto =
                new ErrorResponseDto(
                        request.getDescription(false),
                        HttpStatus.BAD_REQUEST,
                        exception.getMessage(),
                        LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> resourceNotFoundException(ResourceNotFoundException exception, WebRequest request){
        ErrorResponseDto errorResponseDto =
                new ErrorResponseDto(
                        request.getDescription(false),
                        HttpStatus.NOT_FOUND,
                        exception.getMessage(),
                        LocalDateTime.now());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }
}
