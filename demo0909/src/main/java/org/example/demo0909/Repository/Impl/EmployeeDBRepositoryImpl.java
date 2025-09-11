package org.example.demo0909.Repository.Impl;

import java.util.List;
import org.example.demo0909.Repository.EmployeeJpaRepository;
import org.example.demo0909.Repository.EmployeeDBRepository;
import org.example.demo0909.domain.Employee;
import org.example.demo0909.dto.EmployeeDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDBRepositoryImpl implements EmployeeDBRepository {
  @Autowired
  private EmployeeJpaRepository employeeJpaRepository;

  @Override
  public List<Employee> getEmployeeList() {
    return employeeJpaRepository.findAll();
  }

  @Override
  public Employee save(Employee employee) {
    return employeeJpaRepository.save(employee);
  }

  @Override
  public List<Employee> getEmployeeListByGender(String gender) {
    return employeeJpaRepository.findByGender(gender);
  }

  @Override
  public Employee getEmployeeById(int id) {
    return employeeJpaRepository.findById(id).get();
  }

  @Override
  public void deleteEmployee(int id) {
     employeeJpaRepository.deleteById(id);
  }

  @Override
  public Employee updateEmployee(int id, Employee employee) {
    return employeeJpaRepository.save(employee);
  }

  @Override
  public void clear() {
    employeeJpaRepository.deleteAll();
  }
}
