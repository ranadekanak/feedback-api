package org.krsnaa.feedback.repository;

import org.krsnaa.feedback.domain.MedicalCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalCenterRepository extends JpaRepository<MedicalCenter, Long> {

}
