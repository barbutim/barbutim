package cz.cvut.fel.nss.careerpages.security.model;

import cz.cvut.fel.nss.careerpages.model.AbstractUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.*;

/**
 * User details sec
 */
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private AbstractUser user;

    private final Set<GrantedAuthority> authorities;

    /**
     * @param user
     * constructor
     */
    public UserDetails(AbstractUser user) {
        Objects.requireNonNull(user);
        this.user = user;
        this.authorities = new HashSet<>();
        addUserRole();
    }

    /**
     * @param user
     * @param authorities
     * constructor
     */
    public UserDetails(AbstractUser user, Collection<GrantedAuthority> authorities) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(authorities);
        this.user = user;
        this.authorities = new HashSet<>();
        addUserRole();
        this.authorities.addAll(authorities);
    }

    /**
     * add user role
     */
    private void addUserRole() {
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
    }

    /**
     * @return get authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableCollection(authorities);
    }

    /**
     * @return get password
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * @return get username
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * @return is not expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    /**
     * @return is not locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * @return is credentials not expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * @return is enabled
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * @return get user
     */
    public AbstractUser getUser() {
        return user;
    }

    /**
     * erase credentials
     */
    public void eraseCredentials() {
        user.erasePassword();
    }
}
