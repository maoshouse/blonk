package com.maoshouse.blonk.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class LoginRequest {

    @NonNull
    private final String username;
    @NonNull
    private final String password;

}
