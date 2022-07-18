package com.microservice.budget.error.exception.advice;

import com.microservice.budget.error.exception.InvalidCreateAccountRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidCreateAccountRequestAdvice {
    @ResponseBody  //Armando el body de response
    @ExceptionHandler(InvalidCreateAccountRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String invalidCreateAccountRequestHandler(InvalidCreateAccountRequestException ex) {
        return ex.getMessage();
    }
}
