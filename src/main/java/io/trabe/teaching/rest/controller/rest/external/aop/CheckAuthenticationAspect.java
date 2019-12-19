package io.trabe.teaching.rest.controller.rest.external.aop;

import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.trabe.teaching.rest.model.exception.NotAuthenticatedException;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import static io.trabe.teaching.rest.controller.rest.external.aop.Constants.USER_ID_ATTRIBUTE;

@Component
@Aspect
@Order(0)
public class CheckAuthenticationAspect {

    private final String oauthServerLocation;

    public CheckAuthenticationAspect(@Value("${oauth.server}") String oauthServerLocation) {
        this.oauthServerLocation = oauthServerLocation;
    }


    @Pointcut("execution(public * *(..))")
    public void publicMethod() {
    }

    @Pointcut("within(io.trabe.teaching.rest.controller.rest..*) && @target(org.springframework.web.bind.annotation.RestController)")
    public void withinApplicationRestController() {

    }

    @Before("withinApplicationRestController() && publicMethod()")
    public void requireValidAuthorizationToken() {
        String authHeader = getRequest().getHeader("Authorization");
        if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith("Bearer ")) {
            throw new NotAuthenticatedException("Empty Authorization header");
        }
        String token = authHeader.substring(7);
        DecodedJWT jwt = JWT.decode(token);

        JwkProvider provider = new UrlJwkProvider(oauthServerLocation);
        try {
            Jwk jwk = provider.get(jwt.getKeyId());
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
            // Check Signature
            algorithm.verify(jwt);
        } catch (JwkException | SignatureVerificationException e) {
            throw new NotAuthenticatedException("Invalid token", e);
        }
        // Check expiration
        if (jwt.getExpiresAt().before(Calendar.getInstance().getTime())) {
            throw new NotAuthenticatedException("Expired Token");
        }

        getRequest().setAttribute(USER_ID_ATTRIBUTE, jwt.getSubject());

    }


    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

}
