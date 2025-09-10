package org.example.demo0909.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import org.example.demo0909.Exception.CannotCreateException;
import org.example.demo0909.Exception.EmployeeInvalidAgeException;
import org.example.demo0909.Exception.EmployeeNotFoundException;
import org.example.demo0909.Exception.EmployeeResignedException;
import org.example.demo0909.Repository.EmployeeRepository;
import org.example.demo0909.domain.Employee;
import org.example.demo0909.dto.EmployeeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {

  @Mock
  private EmployeeRepository employeeRepository;

  @InjectMocks
  private EmployeeService employeeService;

  @Captor
  private ArgumentCaptor<Employee> employeeCaptor;

  @BeforeEach
  void setUp() {
    employeeRepository.clear();
  }

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
    assertThrows(CannotCreateException.class,()->{employeeService.createEmployee(employeeDTO);});
    verify(employeeRepository,never()).save(any());
  }

  @Test
  void should_set_active_to_true_when_create_given_a_valid_employee(){
    EmployeeDTO employeeDTO = new EmployeeDTO();
    employeeDTO.setAge(29);
    employeeDTO.setSalary(15000);
    employeeDTO.setGender("male");
    employeeDTO.setName("jack");

    Employee employee = new Employee();
    employee.setName("jack");
    employee.setGender("male");
    employee.setAge(29);
    employee.setSalary(15000);
    employee.setId(1);
    employee.setActive(true);
    when(employeeRepository.save(employeeCaptor.capture())).thenReturn(employee);

    Map<String, Object> employee1 = employeeService.createEmployee(employeeDTO);
    assertEquals(1,employee1.get("id"));
    verify(employeeRepository,times(1)).save(any());
  }

  @Test
  void should_set_active_false_when_deleteEmployee_given_valid_id(){
    int id = 1;
    ResponseEntity<Void> response = employeeService.deleteEmployee(id);
    verify(employeeRepository, times(1)).deleteEmployee(id);
    assertEquals(ResponseEntity.noContent().build(), response);
  }

  @Test
  void should_throw_exception_when_updateEmployee_given_a_employee_active_false(){
    Employee employee = new Employee();
    employee.setName("jack");
    employee.setGender("male");
    employee.setAge(20);
    employee.setSalary(5000);
    employee.setId(1);
    employee.setActive(false);
    when(employeeRepository.getEmployeeById(1)).thenReturn(employee);

    EmployeeDTO employeeDTO = new EmployeeDTO();
    employeeDTO.setAge(29);
    employeeDTO.setSalary(15000);
    employeeDTO.setGender("male");
    employeeDTO.setName("jack");

    EmployeeResignedException exception = assertThrows(
      EmployeeResignedException.class, () -> employeeService.updateEmployee(1,employeeDTO));
    assertEquals("this employee has already resigned,cannot update",exception.getMessage());
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
    assertThrows(EmployeeNotFoundException.class, () -> {employeeService.getEmployeeById(3);});
  }
}