package com.baska.UserService.Payloads;


public class SignInResponse {
    String token;
    String userName;
    Long userId;
    String refreshToken;

    public SignInResponse(String token, String userName, Long userId, String refreshToken) {
        this.token = token;
        this.userName = userName;
        this.userId = userId;
        this.refreshToken = refreshToken;
    }

    public SignInResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
