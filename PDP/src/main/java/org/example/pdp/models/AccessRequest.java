package org.example.pdp.models;

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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
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
    private String target;
    private HashMap<String,String> contextInfo;
}
