package org.example.demo0909.controller;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class CompanyControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void should_create_company_and_return_id() throws Exception {
    String requestBody = """
        {
            "name": "spring"
        }
    """;

    mockMvc.perform(MockMvcRequestBuilders.post("/companies")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id").value(1));
  }

  @Test
  void should_return_all_companies_when_get_companies() throws Exception {
    String firstCompanyRequest = """
        {
            "name": "company1"
        }
    """;

    String secondCompanyRequest = """
        {
            "name": "company2"
        }
    """;

    mockMvc.perform(MockMvcRequestBuilders.post("/companies")
        .contentType(MediaType.APPLICATION_JSON)
        .content(firstCompanyRequest))
      .andExpect(status().isCreated());

    mockMvc.perform(MockMvcRequestBuilders.post("/companies")
        .contentType(MediaType.APPLICATION_JSON)
        .content(secondCompanyRequest))
      .andExpect(status().isCreated());

    mockMvc.perform(MockMvcRequestBuilders.get("/companies")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$.length()").value(2))
      .andExpect(jsonPath("$[0].name").value("company1"))
      .andExpect(jsonPath("$[0].id").value(1))
      .andExpect(jsonPath("$[1].name").value("company2"))
      .andExpect(jsonPath("$[1].id").value(2));
  }

  @Test
  void should_return_company_when_get_company_by_id() throws Exception {
    String companyRequest = """
        {
            "name": "company"
        }
    """;

    mockMvc.perform(MockMvcRequestBuilders.post("/companies")
        .contentType(MediaType.APPLICATION_JSON)
        .content(companyRequest))
      .andExpect(status().isCreated());

    // 根据 ID 获取公司
    mockMvc.perform(MockMvcRequestBuilders.get("/companies/1")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.name").value("company"));
  }
}