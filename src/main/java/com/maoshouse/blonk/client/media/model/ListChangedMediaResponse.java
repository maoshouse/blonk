package com.maoshouse.blonk.client.media.model;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@RequiredArgsConstructor
@Value
public class ListChangedMediaResponse {

    private final List<BlonkMedia> blonkMedia;
}
