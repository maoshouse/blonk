package com.maoshouse.blonk.client.network.model;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
@Value
public class DisarmRequest {

    @NonNull
    private final BlonkNetwork blonkNetwork;
}
