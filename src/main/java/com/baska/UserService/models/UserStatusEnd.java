package com.baska.UserService.models;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "userstatusend")
public class UserStatusEnd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Instant endTime;

    public UserStatusEnd(Long id, Instant endTime) {
        this.id = id;
        this.endTime = endTime;
    }

    public UserStatusEnd() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }
}
