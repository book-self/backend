package xyz.bookself.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.bookself.users.domain.User;

import java.util.Collection;
import java.util.Set;

/**
 * User details object used by Spring Security
 */
public class BookselfUserDetails implements UserDetails {

    private final Integer id;
    private final String username;
    private final String password;
    private final Set<GrantedAuthority> grantedAuthority;

    public BookselfUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPasswordHash();
        this.grantedAuthority = Set.of((GrantedAuthority) () -> "ALL_THE_AUTHORITY");
    }

    public BookselfUserDetails(Integer id) {
        this.id = id;
        this.username = null;
        this.password = null;
        this.grantedAuthority = Set.of((GrantedAuthority) () -> "ALL_THE_AUTHORITY");
    }

    public Integer getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthority;
    }

    @Override
    public String getPassword() {
        return password;
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
