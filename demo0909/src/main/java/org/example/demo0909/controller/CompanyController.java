package org.example.demo0909.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.demo0909.Service.CompanyService;
import org.example.demo0909.domain.Company;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private CompanyService companyService;

  @PostMapping("/companies")
  public ResponseEntity<Map<String,Object>> createCompany(@RequestBody Company company) {
    return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createCompany(company));
  }

  @GetMapping("/companies")
  public List<Company> getCompanies() {
    return companyService.getCompanies();
  }

  @GetMapping("/companies/{id}")
  public Company getCompanyById(@PathVariable int id) {
    return companyService.getCompanyById(id);
  }

  @PutMapping("/companies/{id}")
  public Company updateCompany(@PathVariable int id, @RequestBody Company company) {
    return companyService.updateCompany(id, company);
  }

  @DeleteMapping("/companies/{id}")
  public ResponseEntity<Void> deleteCompany(@PathVariable int id) {
    return companyService.deleteCompany(id);
  }

  @GetMapping("/companies/page")
  public Map<String, Object> getCompaniesWithPagination(
    @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "5") int size) {
    return companyService.getCompaniesWithPagination(page,size);
  }
}
