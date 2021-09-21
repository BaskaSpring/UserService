package com.baska.UserService.models;

import javax.persistence.*;
import java.time.Instant;


@Entity
@Table(name = "userstatus")
public class UserStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long userId;
    Long status;
    Long changeUserId;
    Instant timestamp = Instant.now();
    Instant dateBegin;

    public UserStatus(Long id, Long userId, Long status, Long changeUserId, Instant timestamp, Instant dateBegin) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.changeUserId = changeUserId;
        this.timestamp = timestamp;
        this.dateBegin = dateBegin;
    }

    public UserStatus() {
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

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getChangeUserId() {
        return changeUserId;
    }

    public void setChangeUserId(Long changeUserId) {
        this.changeUserId = changeUserId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Instant getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Instant dateBegin) {
        this.dateBegin = dateBegin;
    }
}
