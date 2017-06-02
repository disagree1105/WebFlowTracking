package com.disagree.bean;

/**
 * Created by disagree on 2017/4/17.
 */
public class Visit {
    private String appId;
    private String visitUserId;
    private String visitId;
    private String title;
    private String userIp;
    private String city;
    private String cityCode;
    private String browser;
    private String os;
    private String isp;
    private String rspTime;
    private String timestamp;
    private String visitTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getRspTime() {
        return rspTime;
    }

    public void setRspTime(String rspTime) {
        this.rspTime = rspTime;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getVisitUserId() {
        return visitUserId;
    }

    public void setVisitUserId(String visitUserId) {
        this.visitUserId = visitUserId;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }
}
