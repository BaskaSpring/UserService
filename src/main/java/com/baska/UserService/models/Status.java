package com.baska.UserService.models;

import javax.persistence.*;

@Entity
@Table(name = "status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(length=30)
    EStatus name;


    public Status(Long id, EStatus name) {
        this.id = id;
        this.name = name;
    }

    public Status() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EStatus getName() {
        return name;
    }

    public void setName(EStatus name) {
        this.name = name;
    }
}
