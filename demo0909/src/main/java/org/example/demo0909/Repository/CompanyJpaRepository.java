package org.example.demo0909.Repository;

import org.example.demo0909.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyJpaRepository extends JpaRepository<Company,Integer> {

}
