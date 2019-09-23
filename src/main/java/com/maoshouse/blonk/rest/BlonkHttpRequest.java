package com.maoshouse.blonk.rest;

import com.google.common.base.Preconditions;
import com.maoshouse.blonk.constants.BlonkConstants;
import com.maoshouse.blonk.constants.HttpConstants;
import com.maoshouse.blonk.model.AuthToken;

import java.net.URI;

public class BlonkHttpRequest {
    private static final String USER_AGENT_FORMAT = "%s/%s";

    private final java.net.http.HttpRequest.Builder httpRequestBuilder;

    public static BlonkHttpRequest put(URI uri, String requestJson) {
        return new BlonkHttpRequest(uri).withRequestBody(requestJson);
    }

    public static BlonkHttpRequest post(URI uri, String requestJson) {
        return new BlonkHttpRequest(uri).withRequestBody(requestJson);
    }

    public static BlonkHttpRequest get(URI uri) {
        return new BlonkHttpRequest(uri);
    }

    public static BlonkHttpRequest delete(URI uri) {
        return new BlonkHttpRequest(uri);
    }

    private BlonkHttpRequest(URI uri) {
        Preconditions.checkNotNull(uri);
        httpRequestBuilder = createHttpRequestBuilder().uri(uri);
    }

    public BlonkHttpRequest withHeaders(String... headers) {
        httpRequestBuilder.headers(headers);
        return this;
    }

    public BlonkHttpRequest withHeader(String key, String value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);

        httpRequestBuilder.header(key, value);
        return this;
    }

    public BlonkHttpRequest withAuthToken(AuthToken authToken) {
        Preconditions.checkNotNull(authToken);

        httpRequestBuilder.header(HttpConstants.Headers.TOKEN_AUTH, authToken.getAuthtoken());
        return this;
    }

    public java.net.http.HttpRequest build() {
        return httpRequestBuilder.build();
    }

    private BlonkHttpRequest withRequestBody(String json) {
        Preconditions.checkNotNull(json);

        httpRequestBuilder.POST(java.net.http.HttpRequest.BodyPublishers.ofString(json));
        return this;
    }

    private java.net.http.HttpRequest.Builder createHttpRequestBuilder() {
        return java.net.http.HttpRequest.newBuilder()
                .header(HttpConstants.Headers.CONTENT_TYPE, HttpConstants.ContentType.APPLICATION_JSON)
                .header(HttpConstants.Headers.USER_AGENT, String.format(USER_AGENT_FORMAT, BlonkConstants.APPLICATION_NAME, BlonkConstants.APPLICATION_VERSION));
    }
}
