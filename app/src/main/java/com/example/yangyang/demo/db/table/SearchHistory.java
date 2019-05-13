package com.example.yangyang.demo.db.table;


import com.example.yangyang.demo.TestData.response.main.Student;
import com.example.yangyang.demo.db.AppDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDataBase.class)
public class SearchHistory  extends BaseModel {

    @PrimaryKey(autoincrement = true)
    int contentId;

    @Column
    private String teacherId;

    @Column
    private String name;

    @Column
    private String group;

    @Column
    private Integer id;

    @Column
    private String phoneNumber;

    @Column
    private Long recentlyConnect;

    @Column
    private Long teacherGroup;

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
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

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public Student build(){
        return new Student(name,  group,  id,  phoneNumber,  recentlyConnect, teacherGroup);
    }
}
