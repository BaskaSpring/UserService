package com.baska.UserService.models;


import javax.persistence.*;

@Entity
@Table(name = "groupuser")
public class GroupAndUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long groupId;

    public GroupAndUser(Long id, Long userId, Long groupId) {
        this.id = id;
        this.userId = userId;
        this.groupId = groupId;
    }

    public GroupAndUser() {
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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
