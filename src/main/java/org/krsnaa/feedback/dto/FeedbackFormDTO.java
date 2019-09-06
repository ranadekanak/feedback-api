package org.krsnaa.feedback.dto;

import java.util.List;

public class FeedbackFormDTO {

    private String patientName;

    private String patientMobileNo;

    private String patientCategory;

    private String serviceAvailed;

    private List<AnswerDTO> answers;

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientMobileNo() {
        return patientMobileNo;
    }

    public void setPatientMobileNo(String patientMobileNo) {
        this.patientMobileNo = patientMobileNo;
    }

    public String getPatientCategory() {
        return patientCategory;
    }

    public void setPatientCategory(String patientCategory) {
        this.patientCategory = patientCategory;
    }

    public String getServiceAvailed() {
        return serviceAvailed;
    }

    public void setServiceAvailed(String serviceAvailed) {
        this.serviceAvailed = serviceAvailed;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }
}
