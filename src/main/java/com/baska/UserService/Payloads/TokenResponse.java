package com.baska.UserService.Payloads;

public class TokenResponse {

    Boolean validate;
    Boolean expired;

    public TokenResponse(Boolean validate, Boolean expired) {
        this.validate = validate;
        this.expired = expired;
    }

    public TokenResponse() {
    }

    public Boolean getValidate() {
        return validate;
    }

    public void setValidate(Boolean validate) {
        this.validate = validate;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }
}
