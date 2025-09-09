package org.example.demo0909.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import org.example.demo0909.domain.Employee;
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
  private MockMvc mockMvc;

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


}