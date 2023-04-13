package com.example.projectfifa.module.security;

import com.example.projectfifa.module.security.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)  // username for user
    private String username;

    @Column(name = "password", nullable = false)  // user's password
    private String password;

    @Column(name = "name", nullable = false)  // name of a user
    private String name;


    // bridge table for roles and users. this annotation allows us to create w/out creating new class
    @ManyToMany(fetch = FetchType.EAGER) // Fetch -> in order to prevent Unauthorized exception
    @JoinTable( // joinTable -> creates additional table (bridge table)
            name = "users_and_roles",
            joinColumns = {@JoinColumn(name = "user_id")}, // 1st parameter
            inverseJoinColumns = {@JoinColumn(name = "role_id")} // 2nd parameter
    )
    private Set<Role> roles = new HashSet<>(); // set because user has unique roles

    @Column(name = "is_active", nullable = false, columnDefinition = "boolean default false")
    private Boolean isActive; // if the account is active, by default it is false.

    @Column(name = "is_non_locked", nullable = false, columnDefinition = "boolean default true")
    private Boolean isNonLocked; // if false -> user is not locked

}
