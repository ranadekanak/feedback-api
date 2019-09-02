package org.krsnaa.feedback.dto;

public class GenericResponse {

    private Integer httpCode;

    private String response;

    public GenericResponse(Integer httpCode, String response){
        this.httpCode = httpCode;
        this.response = response;
    }

    public Integer getHttpCode() {
        return httpCode;
    }

    public String getResponse() {
        return response;
    }
}
