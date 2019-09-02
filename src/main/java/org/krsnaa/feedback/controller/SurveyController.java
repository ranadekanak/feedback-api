package org.krsnaa.feedback.controller;

import org.krsnaa.feedback.domain.*;
import org.krsnaa.feedback.dto.AnswerDTO;
import org.krsnaa.feedback.dto.GenericResponse;
import org.krsnaa.feedback.repository.*;
import org.krsnaa.feedback.dto.QuestionDTO;
import org.krsnaa.feedback.dto.SurveyFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/pub/")
public class SurveyController {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private MedicalCenterRepository medicalCenterRepository;

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private AnswersRepository answersRepository;

    @GetMapping("/survey/{id}")
    public ResponseEntity getSurvey(@PathVariable(name = "id") Integer id){
        Map<String, Object> surveyResponse = new LinkedHashMap<>();

        Optional<Survey> survey = surveyRepository.findById(id);
        if(!survey.isPresent() || !survey.get().isStatus()){
            return ResponseEntity.badRequest().body(new GenericResponse(HttpStatus.BAD_REQUEST.value(), "Survey does not exist"));
        }

        Survey existingSurvey = survey.get();
        surveyResponse.put("id", existingSurvey.getId());
        surveyResponse.put("center", existingSurvey.getMedicalCenter());
        surveyResponse.put("name", existingSurvey.getSurveyName());
        surveyResponse.put("status", existingSurvey.isStatus());

        List<Map<String, Object>> questionsWithAnswers = new ArrayList<>();
        Collection<Questions> questions = questionsRepository.findAllBySurveyOrderBySequence(existingSurvey);
        for(Questions question: questions){
            Map<String, Object> questionsWithAnswersMap = new LinkedHashMap<>();
            questionsWithAnswersMap.put("question", question.getText());
            questionsWithAnswersMap.put("type", question.getType());
            questionsWithAnswersMap.put("answer", null);

            questionsWithAnswers.add(questionsWithAnswersMap);
        }
        surveyResponse.put("questionsWithAnswers", questionsWithAnswers);

        return ResponseEntity.ok(surveyResponse);
    }

    @PostMapping("/survey")
    public ResponseEntity createSurvey(@RequestBody SurveyFormDTO surveyFormDTO){
        Optional<MedicalCenter> center = medicalCenterRepository.findById(surveyFormDTO.getCenterId());
        if(!center.isPresent()){
            return ResponseEntity.badRequest().body(new GenericResponse(HttpStatus.BAD_REQUEST.value(),"Medical Center does not exist"));
        }

        Survey existingSurvey = surveyRepository.findByMedicalCenterAndSurveyName(center.get(), surveyFormDTO.getName());
        if(existingSurvey != null && existingSurvey.isStatus()){
            return ResponseEntity.badRequest().body(new GenericResponse(HttpStatus.BAD_REQUEST.value(),"Survey already exists!"));
        }

        Survey survey = new Survey();
        survey.setCreatedBy(1L);
        survey.setCreatedDate(new Date());
        survey.setMedicalCenter(center.get());
        survey.setSurveyName(surveyFormDTO.getName());
        survey.setStatus(Boolean.TRUE);
        survey = surveyRepository.save(survey);

        Collection<Questions> questions = questionsRepository.findAllBySurveyOrderBySequence(survey);
        int existingQuestionsSize = !questions.isEmpty() ? questions.size() : 1;

        if(surveyFormDTO.getQuestions() != null) {
            for (int i = 0; i < surveyFormDTO.getQuestions().size(); i++) {
                Questions question = new Questions();
                QuestionDTO questionDTO = surveyFormDTO.getQuestions().get(i);
                question.setSurvey(survey);
                question.setText(questionDTO.getText());
                question.setType(questionDTO.getType());
                question.setSequence(existingQuestionsSize + i);
                question.setCreatedBy(1L);
                question.setCreatedDate(new Date());

                questionsRepository.save(question);
            }
        }
        return ResponseEntity.ok().body(new GenericResponse(HttpStatus.OK.value(),"Survey created successfully"));
    }

    @PostMapping("/survey/{id}/patient/{patientId}/answers")
    public ResponseEntity saveFeedback(@PathVariable(name = "id") Integer id, @PathVariable(name = "patientId") Integer patientId, @RequestBody List<AnswerDTO> answerDTOS){
        Optional<Survey> survey = surveyRepository.findById(id);
        if(!survey.isPresent() || !survey.get().isStatus()){
            return ResponseEntity.badRequest().body(new GenericResponse(HttpStatus.BAD_REQUEST.value(),"Survey does not exist"));
        }

        Feedback existingFeedback = feedbackRepository.findBySurveyAndPatientId(survey.get(), patientId);
        if(existingFeedback != null){
            return ResponseEntity.badRequest().body(new GenericResponse(HttpStatus.BAD_REQUEST.value(),"Feedback for the survey has been already submitted by the patient"));
        }

        Feedback feedback = new Feedback();
        feedback.setCreatedBy(new Long(patientId));
        feedback.setCreatedDate(new Date());
        feedback.setSurvey(survey.get());
        feedback.setPatientId(patientId);
        feedback = feedbackRepository.save(feedback);

        Collection<Questions> questions = questionsRepository.findAllBySurveyOrderBySequence(survey.get());
        if(questions == null || questions.size() != answerDTOS.size()){
            return ResponseEntity.badRequest().body(new GenericResponse(HttpStatus.BAD_REQUEST.value(),"Please answer all the questions"));
        }

        for(int i = 0; i < answerDTOS.size(); i++){
            final AnswerDTO answerDTO = answerDTOS.get(i);
            Answers answer = new Answers();
            answer.setFeedback(feedback);
            answer.setQuestion(questions.stream().filter(e -> e.getId().equals(answerDTO.getQuestionId())).findFirst().get());
            answer.setResponse(answerDTO.getResponse());
            answer.setCreatedBy(new Long(patientId));
            answer.setCreatedDate(new Date());

            answersRepository.save(answer);
        }

        return ResponseEntity.ok().body(new GenericResponse(HttpStatus.OK.value(),"Answers for survey with id "+id+" and patient with id "+patientId+" saved successfully"));
    }

}
