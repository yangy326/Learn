package com.example.yangyang.demo.TestData.response.login;

public class Data {
    String userId;

    String accessToken;

    String accessTokenExpire;

    String refreshToken;

    String refreshTokenExpire;

    String nickname;
    public Data(String userId, String accessToken, String accessTokenExpire, String refreshToken, String refreshTokenExpire, String nickname) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.accessTokenExpire = accessTokenExpire;
        this.refreshToken = refreshToken;
        this.refreshTokenExpire = refreshTokenExpire;
        this.nickname = nickname;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessTokenExpire() {
        return accessTokenExpire;
    }

    public void setAccessTokenExpire(String accessTokenExpire) {
        this.accessTokenExpire = accessTokenExpire;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshTokenExpire() {
        return refreshTokenExpire;
    }

    public void setRefreshTokenExpire(String refreshTokenExpire) {
        this.refreshTokenExpire = refreshTokenExpire;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}

