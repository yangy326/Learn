package com.example.yangyang.demo.TestData.response.follow;

import java.util.List;

public class FollowData {

    private String studentName;

    private String studentGroup;

    private Integer courseNum;

    private Integer openCourseNum;

    private Integer takeCourseNum;

    private List<FollowRecord> recordVOS;

    public FollowData(String studentName, String studentGroup, Integer courseNum, Integer openCourseNum, Integer takeCourseNum, List<FollowRecord> recordVOS) {
        this.studentName = studentName;
        this.studentGroup = studentGroup;
        this.courseNum = courseNum;
        this.openCourseNum = openCourseNum;
        this.takeCourseNum = takeCourseNum;
        this.recordVOS = recordVOS;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Integer getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(Integer courseNum) {
        this.courseNum = courseNum;
    }

    public Integer getOpenCourseNum() {
        return openCourseNum;
    }

    public void setOpenCourseNum(Integer openCourseNum) {
        this.openCourseNum = openCourseNum;
    }

    public Integer getTakeCourseNum() {
        return takeCourseNum;
    }

    public void setTakeCourseNum(Integer takeCourseNum) {
        this.takeCourseNum = takeCourseNum;
    }

    public List<FollowRecord> getRecordVOS() {
        return recordVOS;
    }

    public void setRecordVOS(List<FollowRecord> recordVOS) {
        this.recordVOS = recordVOS;
    }
}
