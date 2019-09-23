package com.maoshouse.blonk.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.maoshouse.blonk.constants.HttpConstants;
import com.maoshouse.blonk.exception.BlonkClientException;
import com.maoshouse.blonk.exception.NotFoundException;
import com.maoshouse.blonk.exception.ResponseParsingException;
import com.maoshouse.blonk.exception.RestApiException;
import com.maoshouse.blonk.model.AuthToken;
import com.maoshouse.blonk.rest.BlonkHttpRequest;
import com.maoshouse.blonk.rest.BlonkRestApiWrapper;
import com.maoshouse.blonk.rest.ResponseParser;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.net.URI;
import java.net.http.HttpResponse;

@Log4j2
@RequiredArgsConstructor
public class BlonkClientImpl implements BlonkClient {

    @VisibleForTesting
    protected static final String AUTH_TOKEN_MEMBER = "authtoken";
    private static final String API_ENDPOINT_FORMAT = "https://rest.prod.immedia-semi.com/%s";
    private static final String LOGIN_REQUEST_JSON_FORMAT = "{\"email\":\"%s\", \"password\":\"%s\"}";
    private static final String LOGIN_FAILED_EXCEPTION_MESSAGE = "Login failed.";
    @NonNull
    private final BlonkRestApiWrapper blonkRestApiWrapper;
    @NonNull
    private final ResponseParser responseParser;

    @Override
    public AuthToken login(final String userName, final String password) throws BlonkClientException, RestApiException {
        Preconditions.checkNotNull(userName);
        Preconditions.checkNotNull(password);

        try {
            java.net.http.HttpRequest httpRequest = BlonkHttpRequest.post(createApiEndpoint(HttpConstants.Endpoints.LOGIN_ENDPOINT), String.format(LOGIN_REQUEST_JSON_FORMAT, userName, password))
                    .build();
            HttpResponse<String> httpResponse = blonkRestApiWrapper.executeHttpRequest(httpRequest);
            return AuthToken.fromJson(responseParser.getAsStringIfExists(AUTH_TOKEN_MEMBER, httpResponse));
        } catch (NotFoundException | ResponseParsingException exception) {
            throw new BlonkClientException(LOGIN_FAILED_EXCEPTION_MESSAGE, exception);
        }
    }

    private URI createApiEndpoint(final String endpoint) {
        return URI.create(String.format(API_ENDPOINT_FORMAT, endpoint));
    }
}
