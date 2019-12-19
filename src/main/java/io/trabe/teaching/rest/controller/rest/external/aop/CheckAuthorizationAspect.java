package io.trabe.teaching.rest.controller.rest.external.aop;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.trabe.teaching.rest.controller.rest.external.annotation.CheckPrivileges;
import io.trabe.teaching.rest.model.exception.NotEnoughPrivilegesException;
import io.trabe.teaching.rest.model.service.AuthorizationService;

@Component
@Aspect
@Order(1)
public class CheckAuthorizationAspect {

    private final AuthorizationService authorizationService;
    private final AopUtils aopUtils;

    public CheckAuthorizationAspect(AuthorizationService authorizationService,
            AopUtils aopUtils) {
        this.authorizationService = authorizationService;
        this.aopUtils = aopUtils;
    }


    @Pointcut("execution(public * *(..))")
    public void publicMethod() {
    }

    @Pointcut("within(io.trabe.teaching.rest.controller.rest..*) && @target(org.springframework.web.bind.annotation.RestController)")
    public void withinApplicationRestController() {

    }


    @Pointcut("execution(@io.trabe.teaching.rest.controller.rest.external.annotation.CheckPrivileges * *(..))")
    public void methodAnnotatedWithCheckPrivileges() {
    }


    @Before("publicMethod() && withinApplicationRestController() && methodAnnotatedWithCheckPrivileges()")
    public void checkAuthorization(JoinPoint joinPoint) {
        String userName = (String) getRequest().getAttribute(Constants.USER_ID_ATTRIBUTE);
        List<Long> authorizedIds = authorizationService.getAuthorizedUsersForLogin(userName);

        // Retrieve annotation
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        CheckPrivileges annotation = method.getAnnotation(CheckPrivileges.class);

        // Check authorization
        Optional<Long> userIdParameterOptional = aopUtils.getParam(joinPoint, annotation.value(), Long.class);

        if (userIdParameterOptional.isPresent()) {
            if (!authorizedIds.contains(userIdParameterOptional.get())) {
                throw new NotEnoughPrivilegesException(
                        String.format("Api client [%s] is trying to access user [%d] without the proper permissions",
                                userName, userIdParameterOptional.get()));
            }
        }

    }


    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

}
