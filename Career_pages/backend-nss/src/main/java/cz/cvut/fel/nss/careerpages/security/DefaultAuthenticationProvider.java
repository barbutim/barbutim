package cz.cvut.fel.nss.careerpages.security;

import cz.cvut.fel.nss.careerpages.security.model.AuthenticationToken;
import cz.cvut.fel.nss.careerpages.security.model.UserDetails;
import cz.cvut.fel.nss.careerpages.service.security.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Authentication provider def
 */
@Service
public class DefaultAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultAuthenticationProvider.class);

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;


    /**
     * @param userDetailsService
     * @param passwordEncoder
     * @param roleService
     * constructor
     */
    @Autowired
    public DefaultAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    /**
     * @param authentication
     * @return
     * @throws AuthenticationException
     * authenticate
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UserDetails ud = (UserDetails) userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
        if (!passwordEncoder.matches(authentication.getCredentials().toString(), ud.getPassword())) {
            throw new BadCredentialsException("Not validated");
        }
        ud.eraseCredentials();
        return SecurityUtils.setCurrentUser(ud);
    }

    /**
     * @param aClass
     * @return support
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass) ||
                AuthenticationToken.class.isAssignableFrom(aClass);
    }
}
