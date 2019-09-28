package com.maoshouse.blonk.client.media;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.maoshouse.blonk.client.BlonkAuthenticatedClient;
import com.maoshouse.blonk.client.media.model.BlonkMedia;
import com.maoshouse.blonk.client.media.model.ListChangedMediaRequest;
import com.maoshouse.blonk.client.media.model.ListChangedMediaResponse;
import com.maoshouse.blonk.exception.NotFoundException;
import com.maoshouse.blonk.exception.ResponseParsingException;
import com.maoshouse.blonk.exception.RestApiException;
import com.maoshouse.blonk.model.AuthToken;
import com.maoshouse.blonk.rest.BlonkEndpoint;
import com.maoshouse.blonk.rest.BlonkHttpRequest;
import com.maoshouse.blonk.rest.BlonkRestApiWrapper;
import com.maoshouse.blonk.rest.ResponseParser;
import com.maoshouse.blonk.rest.ResponseSerdes;
import lombok.NonNull;

import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class MediaImpl extends BlonkAuthenticatedClient implements Media {

    private static final String MEDIA_ENDPOINT_FORMAT = "api/v1/accounts/%s/media/changed?since=%s&page=%d";
    private static final String MEDIA_MEMBER_NAME = "media";
    @VisibleForTesting
    protected static final int DEFAULT_QUERY_PAGE_VALUE = 1;

    @NonNull
    private BlonkRestApiWrapper blonkRestApiWrapper;
    @NonNull
    private ResponseParser responseParser;
    @NonNull
    private ResponseSerdes responseSerdes;

    public MediaImpl(@NonNull final AuthToken authToken, final BlonkRestApiWrapper blonkRestApiWrapper,
                     final ResponseParser responseParser, final ResponseSerdes responseSerdes) {
        super(authToken);
        this.blonkRestApiWrapper = Preconditions.checkNotNull(blonkRestApiWrapper);
        this.responseParser = Preconditions.checkNotNull(responseParser);
        this.responseSerdes = Preconditions.checkNotNull(responseSerdes);
    }

    @Override
    public ListChangedMediaResponse listChangedMedia(ListChangedMediaRequest request) throws RestApiException,
            NotFoundException, ResponseParsingException {
        Preconditions.checkNotNull(request);

        HttpResponse httpResponse =
                blonkRestApiWrapper.executeHttpRequest(BlonkHttpRequest.get(BlonkEndpoint.createApiEndpoint(
                        buildListPaginatedChangedMediaRequest(request.getBlonkNetwork().getAccountId(),
                                request.getSince(), request.getPage().orElse(DEFAULT_QUERY_PAGE_VALUE))))
                        .withAuthToken(getAuthToken())
                        .build());
        String mediaJsonArray = responseParser.getAsStringIfExists(MEDIA_MEMBER_NAME, httpResponse);
        List<BlonkMedia> blonkMedia = responseSerdes.fromJson(mediaJsonArray, ResponseSerdes.LIST_OF_BLONK_MEDIA_TYPE);
        return new ListChangedMediaResponse(blonkMedia);
    }

    private String buildListPaginatedChangedMediaRequest(String accountId, Instant startingInstant, int page) {
        return String.format(MEDIA_ENDPOINT_FORMAT, accountId, LocalDateTime.ofInstant(startingInstant,
                ZoneId.systemDefault()), page);
    }
}
