package com.maoshouse.blonk.client.media;

import com.google.common.collect.ImmutableList;
import com.maoshouse.blonk.client.media.model.BlonkMedia;
import com.maoshouse.blonk.client.media.model.ListChangedMediaRequest;
import com.maoshouse.blonk.client.media.model.ListChangedMediaResponse;
import com.maoshouse.blonk.client.network.model.BlonkNetwork;
import com.maoshouse.blonk.constants.BlonkTestConstants;
import com.maoshouse.blonk.rest.BlonkRestApiWrapper;
import com.maoshouse.blonk.rest.ResponseParser;
import com.maoshouse.blonk.rest.ResponseSerdes;
import lombok.SneakyThrows;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class MediaClientImplTest {

    private static final String JSON_PLACEHOLDER = "jsonPlaceholder";
    private static final BlonkMedia BLONK_MEDIA = BlonkMedia.builder()
            .id("id")
            .createdTime(LocalDateTime.now())
            .deviceId("deviceId")
            .deviceName("deviceName")
            .watched(true)
            .videoUrl("videoUrl")
            .build();
    private static final List<BlonkMedia> BLONK_MEDIA_LIST = ImmutableList.<BlonkMedia>builder()
            .add(BLONK_MEDIA)
            .build();
    private static final BlonkNetwork BLONK_NETWORK = BlonkNetwork.builder()
            .id("id")
            .name("name")
            .accountId("accountId")
            .armString("armString")
            .build();
    private static final String PAGE_QUERY_FORMAT = "page=%d";
    private static final String SINCE_QUERY_FORMAT = "since=%s";
    private static final Instant SINCE_QUERY_INSTANT = Instant.now();

    @Mock
    private BlonkRestApiWrapper blonkRestApiWrapper;
    @Mock
    private ResponseParser responseParser;
    @Mock
    private ResponseSerdes responseSerdes;
    @Mock
    private HttpResponse httpResponse;

    @DataProvider
    private static Object[] listChangedMediaRequestDataProvider() {
        return ImmutableList.builder()
                .add(new ListChangedMediaRequest(BLONK_NETWORK, SINCE_QUERY_INSTANT, Optional.empty()))
                .add(new ListChangedMediaRequest(BLONK_NETWORK, SINCE_QUERY_INSTANT, Optional.of(1)))
                .add(new ListChangedMediaRequest(BLONK_NETWORK, SINCE_QUERY_INSTANT, Optional.of(2)))
                .build()
                .toArray();
    }

    @BeforeMethod
    void beforeMethod() {
        MockitoAnnotations.initMocks(this);
    }

    @SneakyThrows
    @Test(dataProvider = "listChangedMediaRequestDataProvider")
    void testListChangedMedia_Pages(ListChangedMediaRequest listChangedMediaRequest) {
        int page = listChangedMediaRequest.getPage().orElse(MediaImpl.DEFAULT_QUERY_PAGE_VALUE);
        when(blonkRestApiWrapper.executeHttpRequest(any(HttpRequest.class))).thenReturn(httpResponse);
        when(responseParser.getAsStringIfExists(anyString(), eq(httpResponse))).thenReturn(JSON_PLACEHOLDER);
        when(responseSerdes.fromJson(anyString(), any())).thenReturn(BLONK_MEDIA_LIST);
        final Media media = new MediaImpl(BlonkTestConstants.AUTH_TOKEN, blonkRestApiWrapper, responseParser, responseSerdes);
        ListChangedMediaResponse listChangedMediaResponse = media.listChangedMedia(listChangedMediaRequest);
        assertEquals(listChangedMediaResponse.getBlonkMedia(), BLONK_MEDIA_LIST);
        ArgumentCaptor<HttpRequest> httpRequestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(blonkRestApiWrapper).executeHttpRequest(httpRequestArgumentCaptor.capture());
        HttpRequest httpRequest = httpRequestArgumentCaptor.getValue();
        String queryString = httpRequest.uri().getQuery();
        assertTrue(queryString.contains(String.format(PAGE_QUERY_FORMAT, page)));
        assertTrue(queryString.contains(String.format(SINCE_QUERY_FORMAT, LocalDateTime.ofInstant(SINCE_QUERY_INSTANT, ZoneId.systemDefault()))));
    }
}
