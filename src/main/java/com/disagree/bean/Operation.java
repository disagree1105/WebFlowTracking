package com.disagree.bean;

/**
 * Created by disagree on 2017/4/17.
 */
public class Operation {
    private int operId;
    private String visitUserId;
    private String visitId;
    private String appId;
    private String operType;
    private String operName;
    private String timestamp;

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public int getOperId() {
        return operId;
    }

    public void setOperId(int operId) {
        this.operId = operId;
    }

    public String getVisitUserId() {
        return visitUserId;
    }

    public void setVisitUserId(String visitUserId) {
        this.visitUserId = visitUserId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
