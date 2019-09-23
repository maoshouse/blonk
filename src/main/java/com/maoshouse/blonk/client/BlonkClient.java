package com.maoshouse.blonk.client;

import com.maoshouse.blonk.exception.BlonkClientException;
import com.maoshouse.blonk.exception.RestApiException;
import com.maoshouse.blonk.model.AuthToken;

public interface BlonkClient {

    AuthToken login(final String userName, final String password) throws BlonkClientException, RestApiException;
}
