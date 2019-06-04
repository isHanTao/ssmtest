package com.hant.filter;

import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * @author Hant
 *
 */
public class CorsFilter extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(CorsFilter.class);

    @Setter
    private String allowCredentials;

    @Setter
    private String allowedMethods;

    @Setter
    private String allowedHeaders;

    @Setter
    private String allowedOrigins;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("Adding Access Control Response Headers");
        if (CorsUtils.isCorsRequest(request)) {
            ServletServerHttpRequest wrapRequest = new ServletServerHttpRequest(request);
            if(CorsConfiguration.ALL.equals(allowedOrigins)) {
                response.setHeader("Access-Control-Allow-Origin", wrapRequest.getHeaders().getOrigin());
            } else {
                response.setHeader("Access-Control-Allow-Origin", allowedOrigins);
            }
            response.setHeader("Access-Control-Allow-Credentials", allowCredentials);
            response.setHeader("Access-Control-Allow-Methods", allowedMethods);
            response.setHeader("Access-Control-Allow-Headers", allowedHeaders);
        }
        return true;
    }

}
