package org.krsnaa.feedback.domain;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "feedback")
@EntityListeners(AuditingEntityListener.class)
public class Feedback extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @NotNull
    @Column(name = "PatientName")
    private String patientName;

    @NotNull
    @Column(name = "PatientMobileNo")
    private String patientMobileNo;

    @NotNull
    @Column(name = "PatientCategory")
    private String patientCategory;

    @NotNull
    @Column(name = "ServiceAvailed")
    private String serviceAvailed;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "SurveyId", referencedColumnName = "Id")
    private Survey survey;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }
}
