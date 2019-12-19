package io.trabe.teaching.rest.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Header;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static org.hibernate.validator.internal.util.CollectionHelper.newArrayList;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {
    @Bean
    public Docket v1Api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("public-api-v1")
                .globalResponseMessage(RequestMethod.POST, getResponseMessagesWithDeprecationHeader())
                .globalResponseMessage(RequestMethod.PATCH, getResponseMessagesWithDeprecationHeader())
                .globalResponseMessage(RequestMethod.GET, getResponseMessagesWithDeprecationHeader())
                .globalResponseMessage(RequestMethod.DELETE, getResponseMessagesWithDeprecationHeader())
                .select()
                .apis(RequestHandlerSelectors.basePackage("io.trabe.teaching.rest.controller.rest"))
                .paths(PathSelectors.ant("/api/public/1/**"))
                .build()
                .apiInfo(apiInfoV1())
                .securitySchemes(Collections.singletonList(apiKey()));

    }

    @Bean
    public Docket v2Api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("public-api-v2")
                .globalResponseMessage(RequestMethod.POST, getResponseMessages())
                .globalResponseMessage(RequestMethod.GET, getResponseMessages())
                .globalResponseMessage(RequestMethod.PATCH, getResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, getResponseMessages())
                .select()
                .apis(RequestHandlerSelectors.basePackage("io.trabe.teaching.rest.controller.rest"))
                .paths(PathSelectors.ant("/api/public/2/**"))
                .build()
                .apiInfo(apiInfoV2())
                .securitySchemes(Collections.singletonList(apiKey()))
                .securityContexts(Collections.singletonList(securityContext()))
                .tags(new Tag("account", "Operations related to accounts"));
    }

    private ApiKey apiKey() {
        return new ApiKey("apiKey", "Authorization", "header");
    }

    private ApiInfo apiInfoV1() {
        return new ApiInfoBuilder()
                .title("REST API V1")
                .description("<p>Version 1, the original!<p>" +
                        "<p><strong>This version is currently deprecated and will be retired on 2020.01.15</strong></p>" +
                        "<p>Retired/Changed operations are labeled as deprecated to help developers visualize the " +
                        "changes in v2. Please read the migration guide here and blablabla</p>")
                .termsOfServiceUrl("localhost")
                .version("1.0")
                .build();
    }

    private ApiInfo apiInfoV2() {
        return new ApiInfoBuilder()
                .title("REST API V2")
                .description("Version 2, with a lot of improvements!")
                .termsOfServiceUrl("localhost")
                .version("2.0")
                .build();
    }

    private List<ResponseMessage> getResponseMessages() {

        List<ResponseMessage> responseMessageList = newArrayList();
        responseMessageList.add(new ResponseMessageBuilder()
                .code(401)
                .message(
                        "Unauthorized - Access token not present, invalid or expired. Make sure you provide a valid token using the Bearer schema")
                .build());
        responseMessageList.add(new ResponseMessageBuilder()
                .code(403)
                .message("Forbidden - The provided Access Token doesn't grant the client enough privileges")
                .build());
        return responseMessageList;
    }

    private List<ResponseMessage> getResponseMessagesWithDeprecationHeader() {
        Map<String, Header> headers = new HashMap<>();
        headers.put("Deprecation warning",
                new Header("Deprecated", "Deprecation message blabla bla", new ModelRef("string")));
        List<ResponseMessage> responseMessageList = newArrayList();
        responseMessageList.add(new ResponseMessageBuilder()
                .code(200)
                .headersWithDescription(headers)
                .message(
                        "Accepted  - Everything looks good ")
                .build());
        responseMessageList.add(new ResponseMessageBuilder()
                .code(403)
                .message("Forbidden - The provided Access Token doesn't grant the client enough privileges")
                .headersWithDescription(headers)
                .build());
        responseMessageList.add(new ResponseMessageBuilder()
                .code(403)
                .headersWithDescription(headers)
                .message("Forbidden - The provided Access Token doesn't grant the client enough privileges")
                .build());
        return responseMessageList;
    }


    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.ant("/api/public/2/**"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("apiKey", authorizationScopes));
    }
}
