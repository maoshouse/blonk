package com.maoshouse.blonk.client;

import com.maoshouse.blonk.client.network.Network;
import com.maoshouse.blonk.exception.BlonkClientException;
import com.maoshouse.blonk.exception.RestApiException;
import com.maoshouse.blonk.model.AuthToken;

public interface BlonkClient {

    AuthToken login() throws BlonkClientException, RestApiException;

    Network networks() throws RestApiException, BlonkClientException;
}
