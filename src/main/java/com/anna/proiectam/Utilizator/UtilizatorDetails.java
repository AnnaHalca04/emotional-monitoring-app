package com.anna.proiectam.Utilizator;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UtilizatorDetails implements UserDetails {

    private final Utilizator utilizator;

    public UtilizatorDetails(Utilizator utilizator) {
        this.utilizator = utilizator;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return utilizator.getEmail();
    }

    @Override
    public String getPassword() {
        return utilizator.getParola();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    public Utilizator getUtilizator() {
        return utilizator;
    }
}