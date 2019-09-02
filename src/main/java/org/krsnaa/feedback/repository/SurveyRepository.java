package org.krsnaa.feedback.repository;

import org.krsnaa.feedback.domain.MedicalCenter;
import org.krsnaa.feedback.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Integer> {

    Survey findBySurveyName(String surveyName);

    Survey findByMedicalCenterAndSurveyName(MedicalCenter center, String surveyName);

    Collection<Survey> findByMedicalCenter(MedicalCenter center);

}
