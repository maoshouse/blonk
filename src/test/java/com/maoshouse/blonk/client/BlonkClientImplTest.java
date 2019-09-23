package com.maoshouse.blonk.client;

import com.google.common.collect.ImmutableList;
import com.maoshouse.blonk.constants.BlonkTestConstants;
import com.maoshouse.blonk.exception.BlonkClientException;
import com.maoshouse.blonk.exception.RestApiException;
import com.maoshouse.blonk.rest.BlonkRestApiWrapper;
import com.maoshouse.blonk.rest.ResponseParser;
import lombok.SneakyThrows;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.maoshouse.blonk.client.BlonkClientImpl.AUTH_TOKEN_MEMBER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

public class BlonkClientImplTest {

    @DataProvider
    private static final Object[] testLoginFailure_ResponseParsingErrorDataProvider() {
        return ImmutableList.builder()
                .add(BlonkTestConstants.NOT_FOUND_EXCEPTION)
                .add(BlonkTestConstants.RESPONSE_PARSING_EXCEPTION)
                .build()
                .toArray();
    }

    @Mock
    private BlonkRestApiWrapper blonkRestApiWrapper;
    @Mock
    private ResponseParser responseParser;
    @Mock
    private HttpResponse<String> httpResponse;

    @BeforeMethod
    void beforeMethod() {
        MockitoAnnotations.initMocks(this);
    }

    @SneakyThrows
    @Test
    void testLogin() {
        when(blonkRestApiWrapper.executeHttpRequest(any(HttpRequest.class))).thenReturn(httpResponse);
        when(responseParser.getAsStringIfExists(AUTH_TOKEN_MEMBER, httpResponse)).thenReturn(BlonkTestConstants.AUTH_TOKEN.toJson());
        final BlonkClient blonkClient = new BlonkClientImpl(blonkRestApiWrapper, responseParser);
        assertEquals(blonkClient.login(BlonkTestConstants.USER_NAME, BlonkTestConstants.PASSWORD), BlonkTestConstants.AUTH_TOKEN);
    }

    @SneakyThrows
    @Test
    void testLoginFailure_ApiRequestError() {
        when(blonkRestApiWrapper.executeHttpRequest(any(HttpRequest.class))).thenThrow(BlonkTestConstants.REST_API_EXCEPTION);
        final BlonkClient blonkClient = new BlonkClientImpl(blonkRestApiWrapper, responseParser);
        assertThrows(RestApiException.class, () -> blonkClient.login(BlonkTestConstants.USER_NAME, BlonkTestConstants.PASSWORD));
    }

    @SneakyThrows
    @Test(dataProvider = "testLoginFailure_ResponseParsingErrorDataProvider")
    void testLoginFailure_ResponseParsingError(Exception exception) {
        when(blonkRestApiWrapper.executeHttpRequest(any(HttpRequest.class))).thenReturn(httpResponse);
        when(responseParser.getAsStringIfExists(AUTH_TOKEN_MEMBER, httpResponse)).thenThrow(exception);
        final BlonkClient blonkClient = new BlonkClientImpl(blonkRestApiWrapper, responseParser);
        assertThrows(BlonkClientException.class, () -> blonkClient.login(BlonkTestConstants.USER_NAME, BlonkTestConstants.PASSWORD));
    }
}
