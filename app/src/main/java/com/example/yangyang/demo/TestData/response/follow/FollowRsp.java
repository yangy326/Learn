package com.example.yangyang.demo.TestData.response.follow;

public class FollowRsp {
    private int code;

    private String msg;

    private boolean success ;

    private FollowData data;

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

    public FollowData getData() {
        return data;
    }

    public void setData(FollowData data) {
        this.data = data;
    }
}
