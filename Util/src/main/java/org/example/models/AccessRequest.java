package org.example.models;

import java.util.HashMap;

public class AccessRequest {


    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public HashMap<String, String> getContextInfo() {
        return contextInfo;
    }

    public void setContextInfo(HashMap<String, String> contextInfo) {
        this.contextInfo = contextInfo;
    }


    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String uID) {
        this.subjectID = uID;
    }

    public String getResouceID() {
        return resouceID;
    }

    public void setResouceID(String resouceID) {
        this.resouceID = resouceID;
    }

    private String requestID;
    private String subjectID;
    private String operation;
    private String resouceID;




    private HashMap<String,String> contextInfo;
}
