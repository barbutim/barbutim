package cz.cvut.fel.nss.careerpages.security.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import java.security.Principal;
import java.util.Collection;

/**
 * Authentication token
 */
public class AuthenticationToken extends AbstractAuthenticationToken implements Principal {

    private UserDetails userDetails;

    /**
     * @param authorities
     * @param userDetails
     * constructor
     */
    public AuthenticationToken(Collection<? extends GrantedAuthority> authorities, UserDetails userDetails) {
        super(authorities);
        this.userDetails = userDetails;
        super.setAuthenticated(true);
        super.setDetails(userDetails);
    }

    /**
     * @return get credientials
     */
    @Override
    public String getCredentials() {
        return userDetails.getPassword();
    }

    /**
     * @return get principal
     */
    @Override
    public UserDetails getPrincipal() {
        return userDetails;
    }
}
