package com.portal.centro.API.security;

public class SecurityConstants {
    public static final String SECRET = "Lab_CA";
    public static final long EXPIRATION_TIME = 4 * (24 * 60 * 60 * 1000); // 1 Dia = (24 * 60 * 60 * 1000)
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    public static final String HEADER_USER_STRING = "Credentials";
}
