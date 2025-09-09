package org.example.demo0909.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.demo0909.domain.Employee;
import org.example.demo0909.dto.EmployeeDTO;
import org.springframework.beans.BeanUtils;
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
  private List<Employee> employeeList = new ArrayList<>();

  @PostMapping("/employee")
  public ResponseEntity<Map<String,Object>> createEmployee(@RequestBody EmployeeDTO employeeDTO){
    Employee employee = new Employee();
    BeanUtils.copyProperties(employeeDTO,employee,"id");
    employee.setId(employeeList.size()+1);
    employeeList.add(employee);
    return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id",employee.getId()));
  }

  @GetMapping("/list")
  public  List<Employee> getAllEmployees(){
    return employeeList;
  }

  @GetMapping("/list/{gender}")
  public  List<Employee> getAllEmployeesByGender(@PathVariable String gender){
    return employeeList.stream().filter(employee -> employee.getGender().equals(gender)).toList();
  }

  @GetMapping("/employee/{id}")
  public  Employee getEmployee(@PathVariable int id){
    return employeeList.get(id-1);
  }

  @PutMapping("/employee/{id}")
  public Employee updateEmployee(@PathVariable int id,@RequestBody EmployeeDTO employeeDTO){
    Employee employee = new Employee();
    BeanUtils.copyProperties(employeeDTO,employee,"id");
    employee.setId(id);
    employeeList.set(id-1,employee);
    return employee;
  }

  @DeleteMapping("/employee/{id}")
  public ResponseEntity<Void> deleteEmployee(@PathVariable int id){
    employeeList.remove(id-1);
    return ResponseEntity.noContent().build();
  }


  @GetMapping("/employees")
  public Map<String, Object> getEmployeesWithPagination(
    @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "5") int size) {

    int startIndex = (page - 1) * size;
    int endIndex = Math.min(startIndex + size, employeeList.size());

    List<Employee> pagedEmployees = new ArrayList<>();
    if (startIndex < employeeList.size()) {
      pagedEmployees = employeeList.subList(startIndex, endIndex);
    }

    Map<String, Object> response = new HashMap<>();
    response.put("content", pagedEmployees);
    response.put("page", page);
    response.put("size", size);
    response.put("totalElements", employeeList.size());
    response.put("totalPages", (int) Math.ceil((double) employeeList.size() / size));

    return response;
  }
}
