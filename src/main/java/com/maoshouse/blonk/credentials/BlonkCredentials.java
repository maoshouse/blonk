package com.maoshouse.blonk.credentials;

import com.maoshouse.blonk.exception.CredentialsException;
import com.maoshouse.blonk.model.AuthToken;

public interface BlonkCredentials {
    AuthToken getAuthToken() throws CredentialsException;
}
