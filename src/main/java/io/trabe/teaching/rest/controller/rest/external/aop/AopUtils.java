package io.trabe.teaching.rest.controller.rest.external.aop;

import java.lang.reflect.Method;
import java.util.Optional;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

@Component
public class AopUtils {

    private final ParameterNameDiscoverer parameterNameDiscoverer;

    public AopUtils(ParameterNameDiscoverer parameterNameDiscoverer) {
        this.parameterNameDiscoverer = parameterNameDiscoverer;
    }

    public <T> Optional<T> getParam(JoinPoint joinPoint, String paramName, Class<T> paramClass) {
        Optional<Object> parameterOptional = getParam(joinPoint, paramName);
        return parameterOptional.map(o -> Optional.of((T) o)).orElse(Optional.empty());
    }

    private Optional<Object> getParam(JoinPoint joinPoint, String paramName) {
        try {
            String[] paramNames = parameterNameDiscoverer.getParameterNames(getMethod(joinPoint));
            Object[] values = joinPoint.getArgs();
            for (int i = 0; i < paramNames.length; i++) {
                if (paramName.equals(paramNames[i])) {
                    if (values[i] != null) {
                        return Optional.of(values[i]);
                    } else {
                        return Optional.empty();
                    }
                }
            }
            return Optional.empty();
        } catch (NoSuchMethodException e) {
            return Optional.empty();
        }
    }

    private Method getMethod(JoinPoint joinPoint) throws NoSuchMethodException {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String methodName = joinPoint.getSignature().getName();
        if (method.getDeclaringClass().isInterface()) {
            method = joinPoint.getTarget().getClass().getDeclaredMethod(methodName, method.getParameterTypes());
        }
        return method;
    }
}
