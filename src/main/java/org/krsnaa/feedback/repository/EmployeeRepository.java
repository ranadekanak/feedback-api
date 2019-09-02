package org.krsnaa.feedback.repository;

import org.krsnaa.feedback.domain.Employee;
import org.krsnaa.feedback.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByEmployeeCode(String employeeCode);

    Collection<Employee> findAllByRegion(Region region);
}
