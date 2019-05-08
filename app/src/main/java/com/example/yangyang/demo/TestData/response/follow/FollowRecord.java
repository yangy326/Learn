package com.example.yangyang.demo.TestData.response.follow;

public class FollowRecord {
    private Integer userId;

    private Long createTime;

    private String teacherName;

    private byte isConnected;

    private int audioStatus;

    private String audioUrl;

    private String wordRecord;

    private String tag;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public byte getIsConnected() {
        return isConnected;
    }

    public void setIsConnected(byte isConnected) {
        this.isConnected = isConnected;
    }

    public int getAudioStatus() {
        return audioStatus;
    }

    public void setAudioStatus(int audioStatus) {
        this.audioStatus = audioStatus;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
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
}
