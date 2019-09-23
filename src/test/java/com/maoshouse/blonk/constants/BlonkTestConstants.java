package com.maoshouse.blonk.constants;

import com.maoshouse.blonk.exception.NotFoundException;
import com.maoshouse.blonk.exception.ResponseParsingException;
import com.maoshouse.blonk.exception.RestApiException;
import com.maoshouse.blonk.model.AuthToken;

public class BlonkTestConstants {
    public static final String USER_NAME = "userName";
    public static final String PASSWORD = "password";
    public static final String AUTH_TOKEN_STRING = "authToken";
    public static final AuthToken AUTH_TOKEN = new AuthToken(AUTH_TOKEN_STRING, "message");

    private static final String ERROR_MESSAGE = "errorMessage";
    public static final RestApiException REST_API_EXCEPTION = new RestApiException(ERROR_MESSAGE);
    public static final NotFoundException NOT_FOUND_EXCEPTION = new NotFoundException(ERROR_MESSAGE);
    public static final ResponseParsingException RESPONSE_PARSING_EXCEPTION = new ResponseParsingException(ERROR_MESSAGE, new RuntimeException());
}
