package com.baska.UserService.models;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.Instant;

@Entity
@Table(name = "refreshtokens")
public class RefreshTokens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long userId;

    @Size(max = 30)
    String refreshTokenSecret;

    Long tokenId;

    public RefreshTokens(Long id, Long userId, @Size(max = 30) String refreshTokenSecret, Long tokenId) {
        this.id = id;
        this.userId = userId;
        this.refreshTokenSecret = refreshTokenSecret;
        this.tokenId = tokenId;
    }

    public RefreshTokens() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRefreshTokenSecret() {
        return refreshTokenSecret;
    }

    public void setRefreshTokenSecret(String refreshTokenSecret) {
        this.refreshTokenSecret = refreshTokenSecret;
    }

    public Long getTokenId() {
        return tokenId;
    }

    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }


}
