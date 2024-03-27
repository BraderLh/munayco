package com.pizzeria.munayco.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@MappedSuperclass
public class Audit {
    @Column(name = "status", nullable = false)
    private int status;
    @Column(name = "user_create", length = 50)
    private String userCreate;
    @Column(name = "date_create")
    private Timestamp dateCreate;
    @Column(name = "user_delete",length = 45)
    private String userDelete;
    @Column(name = "date_delete")
    private Timestamp dateDelete;
    @Column(name = "user_modify",length = 45)
    private String userModify;
    @Column(name = "date_modify")
    private Timestamp dateModify;
}
