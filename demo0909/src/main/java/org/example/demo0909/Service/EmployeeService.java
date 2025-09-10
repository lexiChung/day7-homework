package org.example.demo0909.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.demo0909.domain.Employee;
import org.example.demo0909.dto.EmployeeDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

  List<Employee> employeeList = new ArrayList<>();

  public Map<String,Object> createEmployee(EmployeeDTO employeeDTO){
    Employee employee = new Employee();
    BeanUtils.copyProperties(employeeDTO,employee,"id");
    employee.setId(employeeList.size()+1);
    employeeList.add(employee);
    return Map.of("id",employee.getId());
  }

  public List<Employee> getAllEmployees(){
    return employeeList;
  }

  public  List<Employee> getAllEmployeesByGender(String gender){
    return employeeList.stream().filter(employee -> employee.getGender().equals(gender)).toList();
  }

  public  Employee getEmployee(int id){
    return employeeList.get(id-1);
  }

  public Employee updateEmployee(int id,EmployeeDTO employeeDTO){
    Employee employee = new Employee();
    BeanUtils.copyProperties(employeeDTO,employee,"id");
    employee.setId(id);
    employeeList.set(id-1,employee);
    return employee;
  }

  public ResponseEntity<Void> deleteEmployee(int id){
    employeeList.remove(id-1);
    return ResponseEntity.noContent().build();
  }

  public Map<String, Object> getEmployeesWithPagination(int page, int size) {
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

  public void clear(){
    employeeList.clear();
  }
}
