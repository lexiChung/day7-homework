package org.example.demo0909.controller;

import java.util.List;
import java.util.Map;
import org.example.demo0909.Exception.CannotCreateException;
import org.example.demo0909.Exception.EmployeeInvalidAgeException;
import org.example.demo0909.Service.EmployeeService;
import org.example.demo0909.domain.Employee;
import org.example.demo0909.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

  @Autowired
  private EmployeeService  employeeService;

  @PostMapping("/employee")
  public ResponseEntity<Map<String,Object>> createEmployee(@RequestBody EmployeeDTO employeeDTO){
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(employeeDTO));
    } catch (EmployeeInvalidAgeException | CannotCreateException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @GetMapping("/list")
  public  List<Employee> getAllEmployees(){
    return employeeService.getAllEmployees();
  }

  @GetMapping("/list/{gender}")
  public  List<Employee> getAllEmployeesByGender(@PathVariable String gender){
    return employeeService.getAllEmployeesByGender(gender);
  }

  @GetMapping("/employee/{id}")
  public  ResponseEntity<Employee> getEmployeeById(@PathVariable int id){
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.getEmployeeById(id));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @PutMapping("/employee/{id}")
  public Employee updateEmployee(@PathVariable int id,@RequestBody EmployeeDTO employeeDTO){
    return employeeService.updateEmployee(id,employeeDTO);
  }

  @DeleteMapping("/employee/{id}")
  public ResponseEntity<Void> deleteEmployee(@PathVariable int id){
    return employeeService.deleteEmployee(id);
  }


  @GetMapping("/employees")
  public Map<String, Object> getEmployeesWithPagination(
    @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "5") int size) {
    return employeeService.getEmployeesWithPagination(page,size);
  }
}
