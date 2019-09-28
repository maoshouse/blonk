package com.maoshouse.blonk.rest;

import java.net.URI;

public final class BlonkEndpoint {

    private static final String API_ENDPOINT_FORMAT = "https://rest.prod.immedia-semi.com/%s";

    public static URI LOGIN_API_ENDPOINT = createApiEndpoint("login");
    public static URI NETWORKS_API_ENDPOINT = createApiEndpoint("networks");

    public static URI createApiEndpoint(final String endpoint) {
        return URI.create(String.format(API_ENDPOINT_FORMAT, endpoint));
    }
}
