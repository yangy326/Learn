package com.example.yangyang.demo.TestData.request;

public class WordConstruct {

    private Integer userId;

    private String userPhoneNumber;

    private String userGroup;

    private Integer teacherGroup;

    private byte isConnected;

    private Integer callDuration;

    private String wordRecord;

    private String tag;

    private String filename;

    public WordConstruct(Integer userId, String userPhoneNumber, String userGroup, Integer teacherGroup, byte isConnected, Integer callDuration, String wordRecord, String tag, String filename) {
        this.userId = userId;
        this.userPhoneNumber = userPhoneNumber;
        this.userGroup = userGroup;
        this.teacherGroup = teacherGroup;
        this.isConnected = isConnected;
        this.callDuration = callDuration;
        this.wordRecord = wordRecord;
        this.tag = tag;
        this.filename = filename;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public Integer getTeacherGroup() {
        return teacherGroup;
    }

    public void setTeacherGroup(Integer teacherGroup) {
        this.teacherGroup = teacherGroup;
    }

    public byte getIsConnected() {
        return isConnected;
    }

    public void setIsConnected(byte isConnected) {
        this.isConnected = isConnected;
    }

    public Integer getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(Integer callDuration) {
        this.callDuration = callDuration;
    }

    public String getWordRecord() {
        return wordRecord;
    }

    public void setWordRecord(String wordRecord) {
        this.wordRecord = wordRecord;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
