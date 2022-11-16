package com.epam.esm.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@EntityListeners(AuditListener.class)
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "surname")
    private String surname;

    @Column(name = "name")
    private String name;

}
