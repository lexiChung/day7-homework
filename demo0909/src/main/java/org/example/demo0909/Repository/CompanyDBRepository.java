package org.example.demo0909.Repository;

import java.util.List;
import org.example.demo0909.domain.Company;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyDBRepository {
  void save(Company company);

  List<Company> getCompanies();

  Company getCompanyById(int id);

  Company updateCompany(int id,Company company);

  void deleteCompany(int id);

  void clear();
}
