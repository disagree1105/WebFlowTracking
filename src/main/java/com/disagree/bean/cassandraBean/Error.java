package com.disagree.bean.cassandraBean;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 * Created by disagree on 2017/5/11.
 */
@Table
public class Error {
    @PrimaryKey
    public String errorId;

    public String visitId;
    public String appId;
    public String errorMessage;
    public String scriptURI;
    public String lineNumber;
    public String columnNumber;
    public String timestamp;

    public Error(String errorId, String visitId, String appId, String errorMessage, String scriptURI, String lineNumber, String columnNumber, String timestamp) {
        this.errorId = errorId;
        this.visitId = visitId;
        this.appId = appId;
        this.errorMessage = errorMessage;
        this.scriptURI = scriptURI;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.timestamp = timestamp;
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
