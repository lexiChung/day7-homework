package org.example.demo0909.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.demo0909.domain.Company;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping("/companies/{id}")
  public Company getCompanyById(@PathVariable int id) {
    return companies.get(id-1);
  }

  @PutMapping("/companies/{id}")
  public Company updateCompany(@PathVariable int id, @RequestBody Company company) {
    Company existingCompany = companies.get(id-1);
    existingCompany.setName(company.getName());
    return existingCompany;
  }

  @DeleteMapping("/companies/{id}")
  public ResponseEntity<Void> deleteCompany(@PathVariable int id) {
    companies.remove(id-1);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/companies/page")
  public Map<String, Object> getCompaniesWithPagination(
    @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "5") int size) {

    int startIndex = (page - 1) * size;
    int endIndex = Math.min(startIndex + size, companies.size());

    List<Company> pageCompanies = new ArrayList<>();
    if (startIndex < companies.size()) {
      pageCompanies = companies.subList(startIndex, endIndex);
    }

    Map<String, Object> response = new HashMap<>();
    response.put("content", pageCompanies);
    response.put("page", page);
    response.put("size", size);
    response.put("totalElements", companies.size());
    response.put("totalPages", (int) Math.ceil((double) companies.size() / size));

    return response;
  }
}
