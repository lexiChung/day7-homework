package org.example.demo0909.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import org.example.demo0909.Repository.CompanyDBRepository;
import org.example.demo0909.Repository.EmployeeDBRepository;
import org.example.demo0909.domain.Company;
import org.example.demo0909.domain.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

  @Autowired
  private EmployeeDBRepository employeeDBRepository;

  @Autowired
  private CompanyDBRepository companyDBRepository;

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    employeeDBRepository.clear();
  }

  @Test
  void should_return_list_when_get_list()throws Exception{
    Company company = new Company();
    company.setName("oocl");
    companyDBRepository.save(company);

    Employee employee2 = new Employee();
    employee2.setName("jenny");
    employee2.setAge(18);
    employee2.setSalary(5000);
    employee2.setGender("female");
    employee2.setCompanyId(company.getId());
    employeeDBRepository.save(employee2);

    Employee employee = new Employee();
    employee.setName("juicy");
    employee.setAge(18);
    employee.setSalary(5000);
    employee.setGender("female");
    employee.setCompanyId(company.getId());
    employeeDBRepository.save(employee);

    mockMvc.perform(get("/list")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$[0].id").isNumber())
      .andExpect(jsonPath("$[1].id").isNumber());
  }

  @Test
  void should_return_404_when_create_given_invalid_employee() throws Exception{
    String requestBody = """
            {
                  "name" : "jenny",
                  "age" : 31,
                  "salary" : 15000,
                  "gender" :"female"
            }""";
    mockMvc.perform(post("/employee")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
      .andExpect(status().isBadRequest());
  }

  @Test
  void should_return_all_female_employee_when_get_list_given_female()throws Exception {

    Company company = new Company();
    company.setName("oocl");
    companyDBRepository.save(company);

    Employee employee2 = new Employee();
    employee2.setName("jenny");
    employee2.setAge(18);
    employee2.setSalary(5000);
    employee2.setGender("female");
    employee2.setCompanyId(company.getId());
    employeeDBRepository.save(employee2);

    List<Employee> expect = new ArrayList<>();
    Employee employee = new Employee();
    employee.setId(1);
    employee.setName("jenny");
    employee.setAge(18);
    employee.setSalary(5000);
    employee.setGender("female");
    Employee employee1 = new Employee();
    employee1.setId(2);
    expect.add(employee);
    expect.add(employee1);
    String gender = "female";
    mockMvc.perform(get("/list/{gender}", gender)
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$[0].id").isNumber());
  }

  @Test
  void should_return_employee_when_get_employee_given_id() throws Exception {
    Company company = new Company();
    company.setName("oocl");
    companyDBRepository.save(company);

    Employee employee2 = new Employee();
    employee2.setName("jenny");
    employee2.setAge(18);
    employee2.setSalary(5000);
    employee2.setGender("female");
    employee2.setCompanyId(company.getId());
    employeeDBRepository.save(employee2);
    mockMvc.perform(get("/employee/{id}",employee2.getId())
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.name").value("jenny"))
      .andExpect(jsonPath("$.age").value(18))
      .andExpect(jsonPath("$.salary").value(5000))
      .andExpect(jsonPath("$.gender").value("female"));
  }

  @Test
  void should_return_404_when_get_employee_given_invalid_id() throws Exception {
    int id = 1;
    mockMvc.perform(get("/employee/{id}",id)
      .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
  }

  @Test
  void should_update_employee_when_put_employee_given_id_and_employee_dto() throws Exception{
    Company company = new Company();
    company.setName("oocl");
    companyDBRepository.save(company);

    Employee employee2 = new Employee();
    employee2.setName("jenny");
    employee2.setAge(18);
    employee2.setSalary(5000);
    employee2.setGender("female");
    employee2.setActive(true);
    employee2.setCompanyId(company.getId());
    employeeDBRepository.save(employee2);

    Employee expect = new Employee();
    expect.setId(1);
    expect.setName("jenny");
    expect.setAge(20);
    expect.setSalary(8000);
    expect.setGender("female");

    String requestBody1 = """
          {
                "name" : "jenny",
                "age" : 20,
                "salary" : 8000,
                "gender" :"female"
          }""";
    mockMvc.perform(put("/employee/{id}",employee2.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody1))
      .andExpect(jsonPath("$.age").value(expect.getAge()))
      .andExpect(jsonPath("$.salary").value(expect.getSalary()));
  }

  @Test
  void should_return_204_when_delete_employee_given_id() throws Exception {
    Company company = new Company();
    company.setName("oocl");
    companyDBRepository.save(company);

    Employee employee2 = new Employee();
    employee2.setName("jenny");
    employee2.setAge(18);
    employee2.setSalary(5000);
    employee2.setGender("female");
    employee2.setCompanyId(company.getId());
    employeeDBRepository.save(employee2);

    int id = 1;
    mockMvc.perform(delete("/employee/{id}",id)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());
  }

  @Test
  void should_return_paginated_employees_when_get_employees_with_pagination() throws Exception {
    Company company = new Company();
    company.setName("oocl");
    companyDBRepository.save(company);
    for (int i = 1; i <= 10; i++) {
      Employee employee = new Employee();
      employee.setName("jenny"+i);
      employee.setAge(18+i);
      employee.setSalary(5000);
      employee.setGender("female");
      employee.setCompanyId(company.getId());
      employeeDBRepository.save(employee);
    }

    // 测试分页接口
    mockMvc.perform(get("/employees")
        .param("page", "1")
        .param("size", "5")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.content.length()").value(5))
      .andExpect(jsonPath("$.content[0].name").value("jenny1"))
      .andExpect(jsonPath("$.content[4].name").value("jenny5"))
      .andExpect(jsonPath("$.page").value(1))
      .andExpect(jsonPath("$.size").value(5))
      .andExpect(jsonPath("$.totalElements").value(10))
      .andExpect(jsonPath("$.totalPages").value(2));
  }
}