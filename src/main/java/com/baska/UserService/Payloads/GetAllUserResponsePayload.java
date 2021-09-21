package com.baska.UserService.Payloads;


public class GetAllUserResponsePayload {
    Long userId;
    String userName;
    String email;
    String status;
    String lastVisit;
    String blockedTime;
    Integer countWrongPassword;

    public GetAllUserResponsePayload(Long userId, String userName, String email, String status, String lastVisit, String blockedTime, Integer countWrongPassword) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.status = status;
        this.lastVisit = lastVisit;
        this.blockedTime = blockedTime;
        this.countWrongPassword = countWrongPassword;
    }

    public GetAllUserResponsePayload() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(String lastVisit) {
        this.lastVisit = lastVisit;
    }

    public String getBlockedTime() {
        return blockedTime;
    }

    public void setBlockedTime(String blockedTime) {
        this.blockedTime = blockedTime;
    }

    public Integer getCountWrongPassword() {
        return countWrongPassword;
    }

    public void setCountWrongPassword(Integer countWrongPassword) {
        this.countWrongPassword = countWrongPassword;
    }
}
