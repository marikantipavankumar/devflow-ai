package com.devflow.backend.service;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface UserDetails  {
    Collection<? extends GrantedAuthority> getAuthorities();
    String getPassword();
    String getUserName();

    boolean isAccountNonExpired();
    boolean isAccountNonLocked();
    boolean isCredentialsNonExpired();
    boolean isEnabled();
}
