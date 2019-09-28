package com.maoshouse.blonk.rest;

import com.maoshouse.blonk.exception.RestApiException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RequiredArgsConstructor
@Log4j2
public class BlonkRestApiWrapper {

    private static final String REST_API_RESPONSE_CODE_FORMAT = "REST API Response was: %d.";
    private static final String REST_API_REQUEST_FAILURE_MESSAGE = "REST API Request failed.";

    @NonNull
    private final HttpClient httpClient;

    public HttpResponse executeHttpRequest(final HttpRequest httpRequest) throws RestApiException {
        try {
            HttpResponse httpResponse = httpClient.send(httpRequest,
                    java.net.http.HttpResponse.BodyHandlers.ofString());
            checkResponseCode(httpResponse);
            return httpResponse;
        } catch (IOException | InterruptedException exception) {
            throw new RestApiException(REST_API_REQUEST_FAILURE_MESSAGE, exception);
        }
    }

    private void checkResponseCode(final HttpResponse httpResponse) throws RestApiException {
        int statusCode = httpResponse.statusCode();
        if (statusCode < 200 || statusCode >= 300) {
            throw new RestApiException(String.format(REST_API_RESPONSE_CODE_FORMAT, statusCode));
        }
    }
}
