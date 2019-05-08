package com.example.yangyang.demo.TestData.response.log;

import com.example.yangyang.demo.TestData.response.follow.FollowData;

public class RspLog {
    private int code;

    private String msg;

    private boolean success ;

    private AudioData data;

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

    public AudioData getData() {
        return data;
    }

    public void setData(AudioData data) {
        this.data = data;
    }
}
