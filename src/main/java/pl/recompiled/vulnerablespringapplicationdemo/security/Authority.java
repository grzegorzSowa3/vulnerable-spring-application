package pl.recompiled.vulnerablespringapplicationdemo.security;

import org.springframework.security.core.GrantedAuthority;

enum Authority implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
