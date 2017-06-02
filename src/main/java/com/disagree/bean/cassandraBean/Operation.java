package com.disagree.bean.cassandraBean;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 * Created by disagree on 2017/5/11.
 */
@Table
public class Operation {
    @PrimaryKey
    private String operId;
    private String visitUserId;
    private String visitId;
    private String appId;
    private String operType;
    private String operName;
    private String timestamp;

    public Operation(String operId,String visitUserId, String visitId, String appId, String operType, String operName, String timestamp) {
        this.operId = operId;
        this.visitUserId = visitUserId;
        this.visitId = visitId;
        this.appId = appId;
        this.operType = operType;
        this.operName = operName;
        this.timestamp = timestamp;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
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
