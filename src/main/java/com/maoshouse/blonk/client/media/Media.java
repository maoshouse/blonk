package com.maoshouse.blonk.client.media;

import com.maoshouse.blonk.client.media.model.ListChangedMediaRequest;
import com.maoshouse.blonk.client.media.model.ListChangedMediaResponse;
import com.maoshouse.blonk.exception.NotFoundException;
import com.maoshouse.blonk.exception.ResponseParsingException;
import com.maoshouse.blonk.exception.RestApiException;

public interface Media {

    ListChangedMediaResponse listChangedMedia(final ListChangedMediaRequest request) throws RestApiException, NotFoundException, ResponseParsingException;
}
