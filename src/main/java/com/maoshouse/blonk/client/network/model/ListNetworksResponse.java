package com.maoshouse.blonk.client.network.model;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@RequiredArgsConstructor
@Value
public class ListNetworksResponse {

    @NonNull
    private final List<BlonkNetwork> blonkNetworks;
}
