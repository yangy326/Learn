package com.example.yangyang.demo.TestData.response.main;

import java.util.List;

public class StudentResponse {
    private int code;

    private String msg;

    private boolean success ;

    private List<Student> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Student> getList() {
        return data;
    }

    public void setList(List<Student> list) {
        this.data = list;
    }
}
