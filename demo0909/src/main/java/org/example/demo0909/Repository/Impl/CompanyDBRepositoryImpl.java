package org.example.demo0909.Repository.Impl;

import java.util.List;
import org.example.demo0909.Repository.CompanyDBRepository;
import org.example.demo0909.Repository.CompanyJpaRepository;
import org.example.demo0909.domain.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyDBRepositoryImpl implements CompanyDBRepository {
  @Autowired
  private CompanyJpaRepository companyJpaRepository;

  @Override
  public void save(Company company) {
    companyJpaRepository.save(company);
  }

  @Override
  public List<Company> getCompanies() {
    return companyJpaRepository.findAll();
  }

  @Override
  public Company getCompanyById(int id) {
    return companyJpaRepository.findById(id).get();
  }

  @Override
  public Company updateCompany(int id, Company company) {
    company.setId(id);
    return companyJpaRepository.save(company);
  }

  @Override
  public void deleteCompany(int id) {
    companyJpaRepository.deleteById(id);
  }

  @Override
  public void clear() {
    companyJpaRepository.deleteAll();
  }
}
