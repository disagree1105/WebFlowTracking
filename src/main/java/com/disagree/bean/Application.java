package com.disagree.bean;

/**
 * Created by disagree on 2017/4/25.
 */
public class Application {
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getApdex() {
        return apdex;
    }

    public void setApdex(int apdex) {
        this.apdex = apdex;
    }

    public String appId;
    public String uid;
    public String appName;
    public String url;
    public int apdex;


}
