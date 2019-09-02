package org.krsnaa.feedback.repository;

import org.krsnaa.feedback.domain.Feedback;
import org.krsnaa.feedback.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    Feedback findBySurvey(Survey survey);

    Feedback findBySurveyAndPatientId(Survey survey, Integer patientId);

}
