package xyz.bookself.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.bookself.users.domain.User;

import java.util.Collection;
import java.util.Set;

public class BookselfUserDetails implements UserDetails {

    private final String username;
    private final String passwordHash;
    private final Set<GrantedAuthority> grantedAuthority;

    public BookselfUserDetails(User user) {
        this.username = user.getUsername();
        this.passwordHash = user.getPasswordHash();
        this.grantedAuthority = Set.of((GrantedAuthority) () -> "ALL_THE_AUTHORITY");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthority;
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return username;
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
        return true;
    }
}
