package org.krsnaa.feedback.repository;

import org.krsnaa.feedback.domain.Answers;
import org.krsnaa.feedback.domain.Feedback;
import org.krsnaa.feedback.domain.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswersRepository extends JpaRepository<Answers, Integer> {

    Answers findByFeedbackAndQuestion(Feedback feedback, Questions questions);
}
