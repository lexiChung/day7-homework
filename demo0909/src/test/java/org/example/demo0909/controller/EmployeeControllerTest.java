package org.example.demo0909.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import org.example.demo0909.Repository.EmployeeRepository;
import org.example.demo0909.Service.EmployeeService;
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
  private EmployeeController employeeController;

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    employeeRepository.clear();
  }

  @Test
  void should_create_employee_when_post_employee_given_employee_dto() throws Exception {
    String requestBody = """
            {
                  "name" : "jenny",
                  "age" : 18,
                  "salary" : 5000,
                  "gender" :"female"
            }""";
    mockMvc.perform(post("/employee")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1));
  }

  @Test
  void should_return_list_when_get_list()throws Exception{
    String requestBody = """
          {
                "name" : "jenny",
                "age" : 18,
                "salary" : 5000,
                "gender" :"female"
          }""";
    mockMvc.perform(post("/employee")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id").value(1));

    List<Employee> expect = new ArrayList<>();
    Employee employee = new Employee();
    employee.setId(1);
    employee.setName("jenny");
    employee.setAge(18);
    employee.setSalary(5000);
    employee.setGender("female");
    expect.add(employee);

    mockMvc.perform(get("/list")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$[0].id").value(expect.get(0).getId()))
      .andExpect(jsonPath("$[0].name").value(expect.get(0).getName()))
      .andExpect(jsonPath("$[0].age").value(expect.get(0).getAge()))
      .andExpect(jsonPath("$[0].salary").value(expect.get(0).getSalary()))
      .andExpect(jsonPath("$[0].gender").value(expect.get(0).getGender()));

  }

  @Test
  void should_return_all_female_employee_when_get_list_given_female()throws Exception {
    String requestBody = """
          {
                "name" : "jenny",
                "age" : 18,
                "salary" : 5000,
                "gender" :"female"
          }""";
    mockMvc.perform(post("/employee")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody));

    String requestBody1 = """
          {
                "name" : "juicy",
                "age" : 18,
                "salary" : 5000,
                "gender" :"female"
          }""";
    mockMvc.perform(post("/employee")
      .contentType(MediaType.APPLICATION_JSON)
      .content(requestBody1));

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
    mockMvc.perform(get("/list/{gender}",gender)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.length()").value(2))
      .andExpect(jsonPath("$[0].id").value(expect.get(0).getId()))
      .andExpect(jsonPath("$[1].id").value(expect.get(1).getId()));

  }

  @Test
  void should_return_employee_when_get_employee_given_id() throws Exception {
    String requestBody = """
          {
                "name" : "jenny",
                "age" : 18,
                "salary" : 5000,
                "gender" :"female"
          }""";
    mockMvc.perform(post("/employee")
      .contentType(MediaType.APPLICATION_JSON)
      .content(requestBody));

    int id = 1;
    mockMvc.perform(get("/employee/{id}",id)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.name").value("jenny"))
      .andExpect(jsonPath("$.age").value(18))
      .andExpect(jsonPath("$.salary").value(5000))
      .andExpect(jsonPath("$.gender").value("female"));
  }

  @Test
  void should_update_employee_when_put_employee_given_id_and_employee_dto() throws Exception{
    String requestBody = """
          {
                "name" : "jenny",
                "age" : 18,
                "salary" : 5000,
                "gender" :"female"
          }""";
    mockMvc.perform(post("/employee")
      .contentType(MediaType.APPLICATION_JSON)
      .content(requestBody));

    Employee expect = new Employee();
    expect.setId(1);
    expect.setName("jenny");
    expect.setAge(20);
    expect.setSalary(8000);
    expect.setGender("female");

    int id = 1;
    String requestBody1 = """
          {
                "name" : "jenny",
                "age" : 20,
                "salary" : 8000,
                "gender" :"female"
          }""";
    mockMvc.perform(put("/employee/{id}",id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody1))
      .andExpect(jsonPath("$.age").value(expect.getAge()))
      .andExpect(jsonPath("$.salary").value(expect.getSalary()));
  }

  @Test
  void should_return_204_when_delete_employee_given_id() throws Exception {
    String requestBody = """
          {
                "name" : "jenny",
                "age" : 18,
                "salary" : 5000,
                "gender" :"female"
          }""";
    mockMvc.perform(post("/employee")
      .contentType(MediaType.APPLICATION_JSON)
      .content(requestBody));

    int id = 1;
    mockMvc.perform(delete("/employee/{id}",id)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());
  }

  @Test
  void should_return_paginated_employees_when_get_employees_with_pagination() throws Exception {
    // 添加测试数据
    for (int i = 1; i <= 10; i++) {
      String requestBody = String.format("""
          {
              "name": "employee%d",
              "age": 25,
              "salary": 5000,
              "gender": "male"
          }
      """, i);
      mockMvc.perform(post("/employee")
          .contentType(MediaType.APPLICATION_JSON)
          .content(requestBody))
        .andExpect(status().isCreated());
    }

    // 测试分页接口
    mockMvc.perform(get("/employees")
        .param("page", "1")
        .param("size", "5")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.content.length()").value(5))
      .andExpect(jsonPath("$.content[0].name").value("employee1"))
      .andExpect(jsonPath("$.content[4].name").value("employee5"))
      .andExpect(jsonPath("$.page").value(1))
      .andExpect(jsonPath("$.size").value(5))
      .andExpect(jsonPath("$.totalElements").value(10))
      .andExpect(jsonPath("$.totalPages").value(2));
  }
}