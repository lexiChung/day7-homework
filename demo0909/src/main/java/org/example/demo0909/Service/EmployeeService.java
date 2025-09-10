package org.example.demo0909.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.demo0909.Exception.CannotCreateException;
import org.example.demo0909.Exception.EmployeeInvalidAgeException;
import org.example.demo0909.Exception.EmployeeNotFoundException;
import org.example.demo0909.Exception.EmployeeResignedException;
import org.example.demo0909.Repository.EmployeeRepository;
import org.example.demo0909.domain.Employee;
import org.example.demo0909.dto.EmployeeDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

  @Autowired
  private EmployeeRepository employeeRepository;

  public Map<String,Object> createEmployee(EmployeeDTO employeeDTO){

    if(employeeDTO.getAge() < 18) throw new EmployeeInvalidAgeException("employee age at least 18");
    if(employeeDTO.getAge() > 30 && employeeDTO.getSalary() < 20000) throw new CannotCreateException("invalid employee cannot create");
    Employee employee = new Employee();
    BeanUtils.copyProperties(employeeDTO,employee,"id,active");
    employee = employeeRepository.save(employee);
    return Map.of("id",employee.getId());
  }

  public List<Employee> getAllEmployees(){
    return employeeRepository.getEmployeeList();
  }

  public  List<Employee> getAllEmployeesByGender(String gender){
    return employeeRepository.getEmployeeListByGender(gender);
  }

  public  Employee getEmployeeById(int id){
    Employee employeeById = employeeRepository.getEmployeeById(id);
    if(employeeById == null) throw new EmployeeNotFoundException("this employee not exist");
    return employeeById;
  }

  public Employee updateEmployee(int id,EmployeeDTO employeeDTO){
    Employee employeeById = employeeRepository.getEmployeeById(id);
    if(employeeById!=null && !employeeById.isActive()) throw new EmployeeResignedException("this employee has already resigned,cannot update");
    return employeeRepository.updateEmployee(id,employeeDTO);
  }

  public ResponseEntity<Void> deleteEmployee(int id){
    employeeRepository.deleteEmployee(id);
    return ResponseEntity.noContent().build();
  }

  public Map<String, Object> getEmployeesWithPagination(int page, int size) {
    int startIndex = (page - 1) * size;
    List<Employee> employeeList = employeeRepository.getEmployeeList();
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
