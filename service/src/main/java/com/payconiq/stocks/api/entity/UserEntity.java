package com.payconiq.stocks.api.entity;

import javax.persistence.*;

/**
 * User entity.
 *
 * @author Kaan Yamanyar
 */
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;
    @Column(name = "role", nullable = false, length = 32)
    private String role;
    @Column(name = "username", unique = true, nullable = false, length = 32)
    private String username;
    @Column(name = "password", nullable = false, length = 32)
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
