package com.disagree.bean;

/**
 * Created by disagree on 2017/4/17.
 */
public class Error {
    public int id;
    public String errorId;
    public String visitId;
    public String appId;
    public String errorMessage;
    public String scriptURI;
    public String lineNumber;
    public String columnNumber;
    public String timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getErrorId() {
        return errorId;
    }

    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getScriptURI() {
        return scriptURI;
    }

    public void setScriptURI(String scriptURI) {
        this.scriptURI = scriptURI;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(String columnNumber) {
        this.columnNumber = columnNumber;
    }


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
