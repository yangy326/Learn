package com.example.yangyang.demo.db.table;

import com.example.yangyang.demo.db.AppDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
@Table(database = AppDataBase.class)
public class PushFailed extends BaseModel {

    @PrimaryKey(autoincrement = true)
    private int id;

    @Column
    private Integer userId;

    @Column
    private int type;

    @Column
    private String userPhoneNumber;

    @Column
    private String userGroup;

    @Column
    private Integer teacherGroup;

    @Column
    private byte isConnected;

    @Column
    private Integer callDuration;

    @Column
    private String wordRecord;

    @Column
    private String tag;

    @Column
    private String filename;

    @Column
    private int recordId;

    @Column
    private String  uploadUrl;

    @Column
    private String  fileUrl;

    @Column
    private String  contentType;

    @Column
    int  status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
