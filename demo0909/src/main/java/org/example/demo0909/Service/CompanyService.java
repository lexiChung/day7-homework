package org.example.demo0909.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.demo0909.Repository.CompanyDBRepository;
import org.example.demo0909.domain.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

  @Autowired
  private CompanyDBRepository companyDBRepository;

  public Map<String,Object> createCompany(Company company) {
    companyDBRepository.save(company);
    return Map.of("id",company.getId());
  }

  public List<Company> getCompanies() {
    return companyDBRepository.getCompanies();
  }

  public Company getCompanyById(int id) {
    return companyDBRepository.getCompanyById(id);
  }

  public Company updateCompany(int id,Company company) {
    Company companyById = companyDBRepository.getCompanyById(id);
    if(companyById == null) return null;
    return companyDBRepository.updateCompany(id,company);
  }

  public ResponseEntity<Void> deleteCompany(int id) {
    companyDBRepository.deleteCompany(id);
    return ResponseEntity.noContent().build();
  }

  public Map<String, Object> getCompaniesWithPagination(int page,int size) {
    int startIndex = (page - 1) * size;
    List<Company> companies = companyDBRepository.getCompanies();
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
