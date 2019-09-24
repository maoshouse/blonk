package com.maoshouse.blonk.rest;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.maoshouse.blonk.exception.NotFoundException;
import com.maoshouse.blonk.exception.ResponseParsingException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.net.http.HttpResponse;

@RequiredArgsConstructor
public class ResponseParser {
    private static final String MEMBER_NOT_FOUND_MESSAGE_FORMAT = "Member %s was not found in %s.";
    private static final String JSON_PARSE_FAILURE_MESSAGE_FORMAT = "Failed to parse JSON: %s.";

    @NonNull
    private final JsonParser jsonParser;

    public String getAsStringIfExists(final String memberName, final HttpResponse<String> httpResponse) throws NotFoundException, ResponseParsingException {
        Preconditions.checkNotNull(memberName);
        Preconditions.checkNotNull(httpResponse);

        JsonElement jsonElement = getIfExists(memberName, parseStringAsJsonObject(httpResponse.body()));
        String jsonElementAsString = StringUtils.EMPTY;
        if (jsonElement.isJsonObject()) {
            jsonElementAsString = jsonElement.getAsJsonObject().toString();
        } else if (jsonElement.isJsonPrimitive()) {
            jsonElementAsString = jsonElement.getAsJsonPrimitive().getAsString();
        } else if (jsonElement.isJsonArray()) {
            jsonElementAsString = jsonElement.getAsJsonArray().toString();
        }
        return jsonElementAsString;
    }

    public JsonObject getAsJsonObjectIfExists(final String memberName, final HttpResponse<String> httpResponse) throws NotFoundException, ResponseParsingException {
        Preconditions.checkNotNull(memberName);
        Preconditions.checkNotNull(httpResponse);

        return getIfExists(memberName, parseStringAsJsonObject(httpResponse.body())).getAsJsonObject();
    }

    private JsonElement getIfExists(final String memberName, final JsonObject jsonObject) throws NotFoundException {
        if (jsonObject.has(memberName)) {
            return jsonObject.get(memberName);
        } else {
            throw new NotFoundException(String.format(MEMBER_NOT_FOUND_MESSAGE_FORMAT, memberName, jsonObject.toString()));
        }
    }

    private JsonObject parseStringAsJsonObject(final String jsonString) throws ResponseParsingException {
        try {
            return jsonParser.parse(jsonString).getAsJsonObject();
        } catch (JsonSyntaxException exception) {
            throw new ResponseParsingException(String.format(JSON_PARSE_FAILURE_MESSAGE_FORMAT, jsonString), exception);
        }
    }
}
