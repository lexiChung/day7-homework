package org.example.demo0909.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.example.demo0909.Exception.EmployeeInvalidAgeException;
import org.example.demo0909.Exception.EmployeeNotFoundException;
import org.example.demo0909.Repository.EmployeeRepository;
import org.example.demo0909.domain.Employee;
import org.example.demo0909.dto.EmployeeDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {

  @Mock
  private EmployeeRepository employeeRepository;

  @InjectMocks
  private EmployeeService employeeService;

  @Test
  void should_throw_Exception_when_create_given_invalid_employee_age(){
    EmployeeDTO employeeDTO = new EmployeeDTO();
    employeeDTO.setAge(17);
    employeeDTO.setSalary(5000);
    employeeDTO.setGender("male");
    employeeDTO.setName("jack");
    assertThrows(EmployeeInvalidAgeException.class,()->{employeeService.createEmployee(employeeDTO);});

  }

  @Test
  void should_throw_Exception_when_create_given_employee_age_30_and_salary_below_20000(){
    EmployeeDTO employeeDTO = new EmployeeDTO();
    employeeDTO.setAge(31);
    employeeDTO.setSalary(15000);
    employeeDTO.setGender("male");
    employeeDTO.setName("jack");
    assertThrows(EmployeeInvalidAgeException.class,()->{employeeService.createEmployee(employeeDTO);});

  }

  @Test
  void should_find_a_employee_when_getEmployeeByIdById_given_valid_id(){
    Employee employee = new Employee();
    employee.setName("jack");
    employee.setGender("male");
    employee.setAge(20);
    employee.setSalary(5000);
    employee.setId(1);
    when(employeeRepository.getEmployeeById(1)).thenReturn(employee);

    Employee findEmployee = employeeService.getEmployeeById(1);
    assertEquals(employee.getAge(),findEmployee.getAge());
    assertEquals(employee.getId(),findEmployee.getId());
    assertEquals(employee.getName(),findEmployee.getName());
    assertEquals(employee.getGender(),findEmployee.getGender());
    verify(employeeRepository,times(1)).getEmployeeById(1);
  }
  @Test
  void should_throw_Exception_when_getEmployeeById_given_invalid_id() {
    assertThrows(EmployeeNotFoundException.class, () -> {employeeService.getEmployeeById(1);});
  }
}