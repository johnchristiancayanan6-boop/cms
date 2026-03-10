package com.eastwest.security;

import com.eastwest.model.OwnUser;
import com.eastwest.model.enums.RoleType;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Builder
public class CustomUserDetail implements UserDetails {
    private static final long serialVersionUID = 1L;
    private OwnUser user;

    public OwnUser getUser() {
        return user;
    }

    public void setUser(OwnUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user.getRole().getRoleType() == RoleType.ROLE_SUPER_ADMIN) {
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority(RoleType.ROLE_SUPER_ADMIN.getAuthority()));
            authorities.add(new SimpleGrantedAuthority(RoleType.ROLE_CLIENT.getAuthority()));
            return authorities;
        }
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().getRoleType().getAuthority()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isEnabled();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
