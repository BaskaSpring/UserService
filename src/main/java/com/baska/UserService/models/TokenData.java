package com.baska.UserService.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;

@Entity
@Table(name = "tokendata")
public class TokenData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 30)
    private String tokenSecret;

    private Long  userId;

    private Instant expiredTime;

    public TokenData(Long id, @Size(max = 30) String tokenSecret, Long userId, Instant expiredTime) {
        this.id = id;
        this.tokenSecret = tokenSecret;
        this.userId = userId;
        this.expiredTime = expiredTime;
    }

    public TokenData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Instant getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Instant expiredTime) {
        this.expiredTime = expiredTime;
    }
}
