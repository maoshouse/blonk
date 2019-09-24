package com.maoshouse.blonk.rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maoshouse.blonk.client.network.model.BlonkNetwork;

import java.lang.reflect.Type;
import java.util.List;

public class ResponseSerdes {
    public static final Type LIST_OF_BLONK_NETWORKS_TYPE = new TypeToken<List<BlonkNetwork>>() {
    }.getType();

    private static final Gson GSON = new Gson();

    public <T> T fromJson(String json, Type typeOfT) {
        return GSON.fromJson(json, typeOfT);
    }
}
