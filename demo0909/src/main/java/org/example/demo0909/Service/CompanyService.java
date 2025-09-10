package org.example.demo0909.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.demo0909.Repository.CompanyRepository;
import org.example.demo0909.domain.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

  @Autowired
  private CompanyRepository companyRepository;

  public Map<String,Object> createCompany(Company company) {
    companyRepository.save(company);
    return Map.of("id",company.getId());
  }

  public List<Company> getCompanies() {
    return companyRepository.getCompanies();
  }

  public Company getCompanyById(int id) {
    return companyRepository.getCompanyById(id);
  }

  public Company updateCompany(int id,Company company) {
    return companyRepository.updateCompany(id,company);
  }

  public ResponseEntity<Void> deleteCompany(int id) {
    companyRepository.deleteCompany(id);
    return ResponseEntity.noContent().build();
  }

  public Map<String, Object> getCompaniesWithPagination(int page,int size) {

    int startIndex = (page - 1) * size;
    List<Company> companies = companyRepository.getCompanies();
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
