package com.maoshouse.blonk.client;

import com.maoshouse.blonk.exception.RestApiException;
import com.maoshouse.blonk.model.LoginRequest;
import com.maoshouse.blonk.model.LoginResponse;

public interface BlonkClient {

    LoginResponse login(LoginRequest loginRequest) throws RestApiException;
}
