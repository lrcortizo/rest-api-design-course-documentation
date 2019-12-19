package io.trabe.teaching.rest.config;

import java.util.Properties;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import io.swagger.client.ApiClient;
import io.swagger.client.api.AccountApi;
import io.trabe.teaching.rest.model.exception.AccountNotFoundException;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {


    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(10);
        return messageSource;
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }


    //@Override
    //public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
    //    resolvers.add(new ExceptionHandlerExceptionResolver());
    //    resolvers.add(new ResponseStatusExceptionResolver());
    //   resolvers.add(getSimpleMappingExceptionResolver());
    //}


    private SimpleMappingExceptionResolver getSimpleMappingExceptionResolver() {
        Properties exceptionMappings = new Properties();

        exceptionMappings.setProperty(AccountNotFoundException.class.getName(), "error/account-not-found");

        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();

        resolver.setExceptionMappings(exceptionMappings);
        resolver.addStatusCode("error/account-not-found", 404);

        resolver.setDefaultErrorView("error/default");
        resolver.setDefaultStatusCode(500);

        return resolver;
    }

    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(etagFilter());
        registration.addUrlPatterns("/api/*");
        registration.setName("etagFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean(name = "etagFilter")
    public Filter etagFilter() {
        return new ShallowEtagHeaderFilter();
    }

    @Bean
    public ParameterNameDiscoverer parameterNameDiscoverer() {
        return new LocalVariableTableParameterNameDiscoverer();
    }

    @Bean
    public ApiClient apiClient(@Value("${ws.baseUrl}") String baseUrl) {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(baseUrl);
        return apiClient;
    }

    @Bean
    public AccountApi accountApi(ApiClient apiClient) {
        return new AccountApi(apiClient);
    }
}
