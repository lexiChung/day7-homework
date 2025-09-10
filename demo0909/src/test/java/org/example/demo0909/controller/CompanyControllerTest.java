package org.example.demo0909.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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

    mockMvc.perform(MockMvcRequestBuilders.get("/companies/1")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.name").value("company"));
  }

  @Test
  void should_update_company_when_put_company_by_id() throws Exception {
    String createRequest = """
        {
            "name": "company1"
        }
    """;
    mockMvc.perform(MockMvcRequestBuilders.post("/companies")
        .contentType(MediaType.APPLICATION_JSON)
        .content(createRequest));

    String updateRequest = """
        {
            "name": "company2"
        }
    """;
    mockMvc.perform(MockMvcRequestBuilders.put("/companies/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(updateRequest))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.name").value("company2"));
  }

  @Test
  void should_delete_company_when_delete_company_by_id() throws Exception {
    String companyRequest = """
        {
            "name": "company1"
        }
    """;
    mockMvc.perform(MockMvcRequestBuilders.post("/companies")
        .contentType(MediaType.APPLICATION_JSON)
        .content(companyRequest));

    mockMvc.perform(MockMvcRequestBuilders.delete("/companies/1"))
      .andExpect(status().isNoContent());
  }

  @Test
  void should_return_first_page_companies_with_default_pagination() throws Exception {
    for (int i = 1; i <= 8; i++) {
      String companyRequest = String.format("""
            {
                "name": "company%d"
            }
        """, i);

      mockMvc.perform(MockMvcRequestBuilders.post("/companies")
          .contentType(MediaType.APPLICATION_JSON)
          .content(companyRequest))
        .andExpect(status().isCreated());
    }

    mockMvc.perform(MockMvcRequestBuilders.get("/companies/page?page=1&size=5")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.content").isArray())
      .andExpect(jsonPath("$.content.length()").value(5))
      .andExpect(jsonPath("$.page").value(1))
      .andExpect(jsonPath("$.size").value(5))
      .andExpect(jsonPath("$.totalElements").value(8))
      .andExpect(jsonPath("$.totalPages").value(2));
  }
}