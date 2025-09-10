package org.example.demo0909.Repository;

import java.util.ArrayList;
import java.util.List;
import org.example.demo0909.domain.Company;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyRepository {

  List<Company> companyList = new ArrayList<>();

  public void save(Company company){
    company.setId(companyList.size() + 1);
    companyList.add(company);
  }

  public List<Company> getCompanies() {
    return companyList;
  }

  public Company getCompanyById(int id) {
    return companyList.get(id-1);
  }

  public Company updateCompany(int id,Company company) {
    Company existingCompany = companyList.get(id-1);
    existingCompany.setName(company.getName());
    return existingCompany;
  }

  public void deleteCompany(int id) {
    companyList.remove(id - 1);
  }

  public void clear() {
    companyList.clear();
  }
}
