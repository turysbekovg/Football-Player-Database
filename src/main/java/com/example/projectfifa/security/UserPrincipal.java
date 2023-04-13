package com.example.projectfifa.security;

import com.example.projectfifa.module.security.User;
import com.example.projectfifa.module.security.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

    private final User user;

    public UserPrincipal (User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Set<Role> roles = user.getRoles(); // getting list of roles
//
//        // we need to return the type of GrantedAuthority, not type Role (change from Role to GrantedAuthority)
//        return roles.stream().map(v -> new SimpleGrantedAuthority(v.getTitle()))
//                .collect(Collectors.toSet()); // set with roles, w/ type GrantedAuthority

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        user.getRoles().forEach(v -> grantedAuthorityList.add(new SimpleGrantedAuthority(v.getTitle())));

        user.getRoles().forEach(role -> { // we call list of roles, and call forEach
            role.getAuthorities().forEach(authority -> { // for every role we call getAuthorities, and call forEach
                grantedAuthorityList.add(new SimpleGrantedAuthority(authority.getTitle())); // turn each authority to GrantedAuthority and add to list
            });
        });

        return grantedAuthorityList;

    } // method returns list of roles of a user

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getIsNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getIsActive(); // to check whether user is active or not
    }
}
