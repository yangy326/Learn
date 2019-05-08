package com.example.yangyang.demo.TestData.response.log;

import com.example.yangyang.demo.TestData.response.main.Student;

import java.util.List;

public class WordData {
    private int code;

    private String msg;

    private boolean success ;

    private int  data;

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

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
