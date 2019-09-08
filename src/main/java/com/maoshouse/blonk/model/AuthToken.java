package com.maoshouse.blonk.model;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;

@Value
public class AuthToken {
    private static final Type AUTH_TOKEN_TYPE = new TypeToken<AuthToken>() {
    }.getType();

    private final String authtoken;
    private final String message;

    public static AuthToken fromJson(final String json) {
        return new Gson().fromJson(json, AuthToken.class);
    }

    public AuthToken(final String authtoken, final String message) {
        Preconditions.checkState(StringUtils.isNotBlank(authtoken));
        Preconditions.checkState(StringUtils.isNotBlank(message));

        this.authtoken = authtoken;
        this.message = message;
    }

    public String toJson() {
        return new Gson().toJson(this, AUTH_TOKEN_TYPE);
    }
}
