package com.maoshouse.blonk.client.media.model;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Builder
@Value
public class BlonkMedia {

    @NonNull
    private final String id;
    @NonNull
    @SerializedName(value = "created_at")
    private final LocalDateTime createdTime;
    @NonNull
    @SerializedName(value = "device_id")
    private final String deviceId;
    @NonNull
    @SerializedName(value = "device_name")
    private final String deviceName;
    @NonNull
    private final boolean watched;
    @NonNull
    @SerializedName(value = "media")
    private final String videoUrl;
}
