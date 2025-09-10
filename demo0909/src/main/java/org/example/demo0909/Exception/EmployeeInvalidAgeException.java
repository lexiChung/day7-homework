package org.example.demo0909.Exception;

public class EmployeeInvalidAgeException extends RuntimeException{

  public EmployeeInvalidAgeException(String message) {
    super(message);
  }
}
