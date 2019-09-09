package com.maoshouse.blonk.client;

import com.maoshouse.blonk.credentials.BlonkCredentials;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
public class BlonkClientImpl implements BlonkClient {

    @NonNull
    private final BlonkCredentials blonkCredentials;
}
