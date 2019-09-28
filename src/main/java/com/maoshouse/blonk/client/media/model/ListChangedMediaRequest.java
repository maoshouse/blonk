package com.maoshouse.blonk.client.media.model;

import com.maoshouse.blonk.client.network.model.BlonkNetwork;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.Instant;
import java.util.Optional;

@RequiredArgsConstructor
@Value
public class ListChangedMediaRequest {

    @NonNull
    private final BlonkNetwork blonkNetwork;
    @NonNull
    private final Instant since;
    @NonNull
    private final Optional<Integer> page;
}
