package org.krsnaa.feedback.repository;

import org.krsnaa.feedback.domain.Questions;
import org.krsnaa.feedback.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions, Integer> {

    Collection<Questions> findAllBySurveyOrderBySequence(Survey survey);

    Questions findBySurveyAndId(Survey survey, Integer id);
}
