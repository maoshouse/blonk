package com.maoshouse.blonk.client;

import com.maoshouse.blonk.model.AuthToken;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class BlonkAuthenticatedClient {

    @NonNull
    protected final AuthToken authToken;
}
