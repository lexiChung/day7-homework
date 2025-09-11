package org.example.demo0909.Repository;

import java.util.List;
import org.example.demo0909.domain.Employee;
import org.example.demo0909.dto.EmployeeDTO;

public interface EmployeeDBRepository {
  List<Employee> getEmployeeList();

  Employee save(Employee employee);

  List<Employee> getEmployeeListByGender(String gender);

  Employee getEmployeeById(int id);

  void deleteEmployee(int id);

  Employee updateEmployee(int id, Employee employee);

  void clear();
}
