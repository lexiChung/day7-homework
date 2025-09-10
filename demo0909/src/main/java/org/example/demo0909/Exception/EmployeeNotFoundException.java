package org.example.demo0909.Exception;

public class EmployeeNotFoundException extends RuntimeException{

  public EmployeeNotFoundException(String message) {
    super(message);
  }
}
