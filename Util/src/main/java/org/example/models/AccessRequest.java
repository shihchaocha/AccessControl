package org.example.models;

import java.util.HashMap;

public class AccessRequest {


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

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

    private String requestID;
    private String uid;
    private String operation;

    public String getResouceid() {
        return resouceid;
    }

    public void setResouceid(String resouceid) {
        this.resouceid = resouceid;
    }

    private String resouceid;
    private HashMap<String,String> contextInfo;
}
