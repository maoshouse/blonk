package com.maoshouse.blonk.credentials;

import com.google.common.base.Preconditions;
import com.maoshouse.blonk.exception.CredentialsException;
import com.maoshouse.blonk.exception.RestApiException;
import com.maoshouse.blonk.model.AuthToken;
import com.maoshouse.blonk.rest.BlonkRestApiWrapper;

public class BasicBlonkCredentials implements BlonkCredentials {

    private final String email;
    private final String password;
    private final BlonkRestApiWrapper blonkRestApiWrapper;

    public BasicBlonkCredentials(final BlonkRestApiWrapper blonkRestApiWrapper, final String email, final String password) {
        this.blonkRestApiWrapper = Preconditions.checkNotNull(blonkRestApiWrapper);
        this.email = Preconditions.checkNotNull(email);
        this.password = Preconditions.checkNotNull(password);
    }

    @Override
    public AuthToken getAuthToken() throws CredentialsException {
        try {
            return blonkRestApiWrapper.login(email, password);
        } catch (RestApiException exception) {
            throw new CredentialsException(exception);
        }
    }
}
