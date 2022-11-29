package com.sunil.blog.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    private ApiKey getApiKey() {
        return new ApiKey("JWT", AppConstants.AUTH_HEADER_NAME, "header");
    }

    private SecurityContext getSecurityContext() {
        return SecurityContext.builder().securityReferences(getSecurityReferences()).build();
    }

    private List<SecurityReference> getSecurityReferences() {
        AuthorizationScope authScope = new AuthorizationScope("global", "accessEverything");
        return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] { authScope }));
    }

    @Bean
    public Docket getDocket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo())
                .securityContexts(Arrays.asList(getSecurityContext())).securitySchemes(Arrays.asList(getApiKey()))
                .select()
                .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(AppConstants.APP_TITLE,
                AppConstants.APP_DESCRIPTION,
                AppConstants.APP_VERSION, null,
                new Contact(AppConstants.CONTACT_NAME, AppConstants.CONTACT_WEBSITE, AppConstants.CONTACT_EMAIL), null,
                null, Collections.emptyList());
    }
}
