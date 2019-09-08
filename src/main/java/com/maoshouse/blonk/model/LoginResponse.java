package com.maoshouse.blonk.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class LoginResponse {

    @NonNull
    private final AuthToken authToken;
}
