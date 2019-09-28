package com.maoshouse.blonk.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import com.maoshouse.blonk.client.media.model.BlonkMedia;
import com.maoshouse.blonk.client.network.model.BlonkNetwork;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public class ResponseSerdes {

    public static final Type LIST_OF_BLONK_NETWORKS_TYPE = new TypeToken<List<BlonkNetwork>>() {
    }.getType();
    public static final Type LIST_OF_BLONK_MEDIA_TYPE = new TypeToken<List<BlonkMedia>>() {
    }.getType();

    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
            (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) ->
                    ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime()).create();

    public <T> T fromJson(String json, Type typeOfT) {
        return GSON.fromJson(json, typeOfT);
    }
}
