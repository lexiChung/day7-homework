package org.example.demo0909.common;

import org.example.demo0909.Exception.CannotCreateException;
import org.example.demo0909.Exception.EmployeeInvalidAgeException;
import org.example.demo0909.Exception.EmployeeResignedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(EmployeeResignedException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleEmployeeResignedException(Exception e){
    return e.getMessage();
  }

  @ExceptionHandler(EmployeeInvalidAgeException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleEmployeeInvalidAgeException(Exception e){
    return e.getMessage();
  }

  @ExceptionHandler(CannotCreateException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleCannotCreateException(Exception e){
    return e.getMessage();
  }
}
