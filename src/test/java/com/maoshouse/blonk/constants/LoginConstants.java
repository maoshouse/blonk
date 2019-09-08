package com.maoshouse.blonk.constants;

import com.maoshouse.blonk.model.AuthToken;

public class LoginConstants {
    public static final String USER_NAME = "userName";
    public static final String PASSWORD = "password";
    public static final String AUTH_TOKEN_STRING = "authToken";
    public static final AuthToken AUTH_TOKEN = new AuthToken(AUTH_TOKEN_STRING, "message");
}
