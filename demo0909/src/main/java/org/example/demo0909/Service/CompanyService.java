package org.example.demo0909.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.demo0909.domain.Company;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class CompanyService {
  List<Company> companies = new ArrayList<>();

  public Map<String,Object> createCompany(Company company) {
    company.setId(companies.size()+1);
    companies.add(company);
    return Map.of("id",company.getId());
  }

  public List<Company> getCompanies() {
    return companies;
  }

  public Company getCompanyById(int id) {
    return companies.get(id-1);
  }

  public Company updateCompany(int id,Company company) {
    Company existingCompany = companies.get(id-1);
    existingCompany.setName(company.getName());
    return existingCompany;
  }

  public ResponseEntity<Void> deleteCompany(int id) {
    companies.remove(id-1);
    return ResponseEntity.noContent().build();
  }

  public Map<String, Object> getCompaniesWithPagination(int page,int size) {

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

  public void clear() {
    companies.clear();
  }
}
