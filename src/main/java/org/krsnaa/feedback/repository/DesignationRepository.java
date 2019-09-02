package org.krsnaa.feedback.repository;

import org.krsnaa.feedback.domain.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Integer> {

}
