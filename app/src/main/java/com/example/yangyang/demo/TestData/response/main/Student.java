package com.example.yangyang.demo.TestData.response.main;

import java.io.Serializable;

public class Student implements Serializable {

    private String name;

    private String group;

    private Integer id;

    private String phoneNumber;

    private Long recentlyConnect;

    private Long teacherGroup;

    public Student(String name, String group, Integer id, String phoneNumber, Long recentlyConnect, Long teacherGroup) {
        this.name = name;
        this.group = group;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.recentlyConnect = recentlyConnect;
        this.teacherGroup = teacherGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getRecentlyConnect() {
        return recentlyConnect;
    }

    public void setRecentlyConnect(Long recentlyConnect) {
        this.recentlyConnect = recentlyConnect;
    }

    public Long getTeacherGroup() {
        return teacherGroup;
    }

    public void setTeacherGroup(Long teacherGroup) {
        this.teacherGroup = teacherGroup;
    }
}
