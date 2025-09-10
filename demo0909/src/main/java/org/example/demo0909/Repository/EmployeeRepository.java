package org.example.demo0909.Repository;

import java.util.ArrayList;
import java.util.List;
import org.example.demo0909.domain.Employee;
import org.example.demo0909.dto.EmployeeDTO;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository {
  List<Employee> employeeList = new ArrayList<>();

  public Employee save(Employee employee){
    employee.setId(employeeList.size()+1);
    employee.setActive(true);
    employeeList.add(employee);
    return employee;
  }

  public List<Employee> getEmployeeList(){
    return employeeList;
  }

  public List<Employee> getEmployeeListByGender(String gender){
    return employeeList.stream().filter(employee -> employee.getGender().equals(gender)).toList();
  }

  public Employee getEmployeeById(int id){
    return employeeList.get(id-1);
  }

  public Employee updateEmployee(int id, EmployeeDTO employeeDTO){
    Employee employee1 = employeeList.stream().filter(e -> e.getId() == id).findFirst()
      .orElse(null);
    if (employee1 != null) {
      employee1.setAge(employeeDTO.getAge());
      employee1.setSalary(employeeDTO.getSalary());
    }
    return employee1;
  }

  public void deleteEmployee(int id){
    employeeList.remove(id-1);
  }

  public void clear(){
    employeeList.clear();
  }
}
