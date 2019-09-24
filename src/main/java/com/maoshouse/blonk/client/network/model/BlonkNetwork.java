package com.maoshouse.blonk.client.network.model;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
@Builder
@Value
public class BlonkNetwork {

    @NonNull
    private final String id;
    @NonNull
    private final String name;
    @NonNull
    @SerializedName(value = "account_id")
    private final String accountId;
    @NonNull
    @SerializedName(value = "arm_string")
    private final String armString;
}
