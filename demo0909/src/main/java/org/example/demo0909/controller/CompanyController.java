package org.example.demo0909.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.example.demo0909.domain.Company;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyController {
  List<Company> companies = new ArrayList<>();

  @PostMapping("/companies")
  public ResponseEntity<Map<String,Object>> createCompany(@RequestBody Company company) {
    company.setId(companies.size()+1);
    companies.add(company);
    return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id",company.getId()));
  }

  @GetMapping("/companies")
  public List<Company> getCompanies() {
    return companies;
  }
}
