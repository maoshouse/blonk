package com.maoshouse.blonk.credentials;

import com.google.common.base.Preconditions;
import com.maoshouse.blonk.client.BlonkClient;
import com.maoshouse.blonk.exception.BlonkClientException;
import com.maoshouse.blonk.exception.CredentialsException;
import com.maoshouse.blonk.exception.RestApiException;
import com.maoshouse.blonk.model.AuthToken;

public class BasicBlonkCredentials implements BlonkCredentials {

    private final String email;
    private final String password;
    private final BlonkClient blonkClient;

    public BasicBlonkCredentials(final BlonkClient blonkClient, final String email, final String password) {
        this.blonkClient = Preconditions.checkNotNull(blonkClient);
        this.email = Preconditions.checkNotNull(email);
        this.password = Preconditions.checkNotNull(password);
    }

    @Override
    public AuthToken getAuthToken() throws CredentialsException {
        try {
            return blonkClient.login(email, password);
        } catch (BlonkClientException | RestApiException exception) {
            throw new CredentialsException(exception);
        }
    }
}
