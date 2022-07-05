package cz.cvut.fel.nss.careerpages.security;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * session timeout
 */
@WebListener
public class SessionTimeoutManager implements HttpSessionListener {

    /**
     * @param httpSessionEvent
     * create session
     */
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().setMaxInactiveInterval(SecurityConstants.SESSION_TIMEOUT);
    }

    /**
     * @param httpSessionEvent
     * destroy session
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
    }
}
