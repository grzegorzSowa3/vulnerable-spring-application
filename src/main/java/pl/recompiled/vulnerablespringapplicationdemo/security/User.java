package pl.recompiled.vulnerablespringapplicationdemo.security;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "app_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class User implements Persistable<UUID>, UserDetails {

    @Id
    private UUID id;
    @Transient
    private boolean isNew;
    @Column(unique = true)
    private String username;
    private String password;
    private String activationToken;

    @Convert(converter = AuthoritiesToStringConverter.class)
    private Set<Authority> authorities;

    public static User newInstance(String username,
                                   String password,
                                   Set<Authority> authorities,
                                   String activationToken) {
        final User user = new User();
        user.id = UUID.randomUUID();
        user.isNew = true;
        user.username = username;
        user.password = password;
        user.authorities = authorities;
        user.activationToken = activationToken;
        return user;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activationToken == null;
    }

    void activate() {
        this.activationToken = null;
    }
}
