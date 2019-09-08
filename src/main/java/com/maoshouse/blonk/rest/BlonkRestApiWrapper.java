package com.maoshouse.blonk.rest;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.maoshouse.blonk.constants.BlonkConstants;
import com.maoshouse.blonk.constants.HttpConstants;
import com.maoshouse.blonk.exception.NotFoundException;
import com.maoshouse.blonk.exception.ResponseParsingException;
import com.maoshouse.blonk.exception.RestApiException;
import com.maoshouse.blonk.model.AuthToken;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RequiredArgsConstructor
@Log4j2
public class BlonkRestApiWrapper {
    @VisibleForTesting
    protected static final String AUTH_TOKEN_MEMBER = "authtoken";
    private static final String REST_API_RESPONSE_CODE_FORMAT = "REST API Response was: %d.";
    private static final String REST_API_REQUEST_FAILURE_MESSAGE = "REST API Request failed.";
    private static final String API_ENDPOINT_FORMAT = "https://rest.prod.immedia-semi.com/%s";
    private static final String LOGIN_ENDPOINT = "login";
    private static final String LOGIN_REQUEST_JSON_FORMAT = "{\"email\":\"%s\", \"password\":\"%s\"}";
    private static final String USER_AGENT_FORMAT = "%s/%s";

    @NonNull
    private final HttpClient httpClient;
    @NonNull
    private final ResponseParser responseParser;

    public AuthToken login(final String userName, final String password) throws RestApiException {
        Preconditions.checkNotNull(userName);
        Preconditions.checkNotNull(password);

        try {
            HttpRequest httpRequest = HttpRequest.newBuilder(createApiEndpoint(LOGIN_ENDPOINT))
                    .header(HttpConstants.Headers.CONTENT_TYPE, HttpConstants.ContentType.APPLICATION_JSON)
                    .header(HttpConstants.Headers.USER_AGENT, String.format(USER_AGENT_FORMAT, BlonkConstants.APPLICATION_NAME, BlonkConstants.APPLICATION_VERSION))
                    .POST(HttpRequest.BodyPublishers.ofString(String.format(LOGIN_REQUEST_JSON_FORMAT, userName, password)))
                    .build();
            HttpResponse<String> httpResponse = sendHttpRequest(httpRequest);
            return AuthToken.fromJson(responseParser.getAsStringIfExists(AUTH_TOKEN_MEMBER, httpResponse));
        } catch (NotFoundException | ResponseParsingException | IOException | InterruptedException exception) {
            throw new RestApiException(REST_API_REQUEST_FAILURE_MESSAGE, exception);
        }
    }

    private URI createApiEndpoint(final String endpoint) {
        return URI.create(String.format(API_ENDPOINT_FORMAT, endpoint));
    }

    private HttpResponse sendHttpRequest(final HttpRequest httpRequest) throws RestApiException, IOException, InterruptedException {
        HttpResponse httpResponse = httpClient.send(httpRequest, java.net.http.HttpResponse.BodyHandlers.ofString());
        checkResponseCode(httpResponse);
        return httpResponse;
    }

    private void checkResponseCode(final HttpResponse httpResponse) throws RestApiException {
        int statusCode = httpResponse.statusCode();
        if (statusCode < 200 || statusCode >= 300) {
            throw new RestApiException(String.format(REST_API_RESPONSE_CODE_FORMAT, statusCode));
        }
    }
}
