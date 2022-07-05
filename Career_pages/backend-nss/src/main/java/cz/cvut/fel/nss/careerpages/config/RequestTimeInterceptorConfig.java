package cz.cvut.fel.nss.careerpages.config;

import cz.cvut.fel.nss.careerpages.rest.interceptor.RequestTimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Interceptor config
 */
@Component
public class RequestTimeInterceptorConfig extends WebMvcConfigurerAdapter {

    RequestTimeInterceptor requestTimeInterceptor;

    /**
     * @param requestTimeInterceptor
     * Time interceptor config
     */
    @Autowired
    public RequestTimeInterceptorConfig(RequestTimeInterceptor requestTimeInterceptor) {
        this.requestTimeInterceptor = requestTimeInterceptor;
    }

    /**
     * @param registry
     * new interceptor
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestTimeInterceptor);
    }
}

