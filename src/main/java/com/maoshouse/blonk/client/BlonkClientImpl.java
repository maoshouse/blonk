package com.maoshouse.blonk.client;

import com.google.common.base.Preconditions;
import com.maoshouse.blonk.exception.RestApiException;
import com.maoshouse.blonk.model.LoginRequest;
import com.maoshouse.blonk.model.LoginResponse;
import com.maoshouse.blonk.rest.BlonkRestApiWrapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
public class BlonkClientImpl implements BlonkClient {

    @NonNull
    private final BlonkRestApiWrapper blonkRestApiWrapper;

    public LoginResponse login(LoginRequest loginRequest) throws RestApiException {
        Preconditions.checkNotNull(loginRequest);

        return LoginResponse.builder()
                .authToken(blonkRestApiWrapper.login(loginRequest.getUsername(), loginRequest.getPassword()))
                .build();
    }
}
