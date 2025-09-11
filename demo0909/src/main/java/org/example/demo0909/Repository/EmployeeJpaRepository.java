package org.example.demo0909.Repository;

import java.util.List;
import org.example.demo0909.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeJpaRepository extends JpaRepository<Employee, Integer> {

  List<Employee> findByGender(String gender);
}
