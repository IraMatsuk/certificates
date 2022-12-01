package com.epam.esm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type User.
 */
@Entity
@EntityListeners(AuditListener.class)
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "username")
    private String userName;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Order> orders;

    /**
     * Instantiates a new User.
     */
    public User() {
        orders = new LinkedHashSet<>();
    }

    /**
     * Instantiates a new User.
     *
     * @param id the id
     */
    public User(long id) {
        this.id = id;
        orders = new LinkedHashSet<>();
    }
}
