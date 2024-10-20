package org.example.models;

public class AccessResponse {
    public AccessResponse(String requestID, int result, String message) {
        this.requestID = requestID;
        this.result = result;
        this.message = message;
    }

    public AccessResponse() {

    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String requestID;
    private int result;
    private String message;
}
