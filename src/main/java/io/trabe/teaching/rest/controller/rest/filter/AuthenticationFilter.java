package io.trabe.teaching.rest.controller.rest.filter;

import java.io.IOException;
import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class AuthenticationFilter implements Filter {
    private static final String BEARER_SCHEMA = "Bearer";
    private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);
    private final String oauthServerLocation;

    public AuthenticationFilter(@Value("${oauth.server}") String oauthServerLocation) {
        this.oauthServerLocation = oauthServerLocation;

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String authHeader = ((HttpServletRequest) request).getHeader("Authorization");

        if ((authHeader == null) || !authHeader.startsWith(BEARER_SCHEMA)) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            try {
                String token = authHeader.substring(7);
                DecodedJWT jwt = JWT.decode(token);

                JwkProvider provider = new UrlJwkProvider(oauthServerLocation);
                Jwk jwk = provider.get(jwt.getKeyId());
                Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);

                // Check Signature
                algorithm.verify(jwt);

                // Check expiration
                if (jwt.getExpiresAt().after(Calendar.getInstance().getTime())) {
                    chain.doFilter(request, response);
                } else {
                    throw new RuntimeException("Exired token!");
                }
            } catch (Exception e) {
                //Invalid signature/claims
                log.debug("Invalid access token", e);
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }
}
