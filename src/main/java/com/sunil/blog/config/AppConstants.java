package com.sunil.blog.config;

public class AppConstants {
    public static final String PAGE_NUMBER = "0";
    public static final String PAGE_SIZE = "10";
    public static final String SORT_BY = "addedDate";
    public static final String SORT_DIR = "DESC";

    /////
    ///
    public static final String AUTH_HEADER_NAME = "Authorization";
    public static final String BEARER = "Bearer";

    ///
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    //// Roles
    //
    public static final Integer USER_ROLE_ID = 501;
    public static final Integer ADMIN_ROLE_ID = 502;

    /// Swagger Docs Constants
    ///
    public static final String APP_TITLE = "Blog App : Spring Boot";
    public static final String APP_DESCRIPTION = "Project build to learn REST Api building through Spring Boot, Security with Spring Security, Database Connection through Spring Boot JPA, Database - MySQL";

    public static final String APP_VERSION = "1.0.0";
    public static final String CONTACT_NAME = "Sunil Poonia";
    public static final String CONTACT_WEBSITE = "https://linkedin.com/in/sunilpoonia";
    public static final String CONTACT_EMAIL = "sunil.poonia.2269@gmail.com";

}
