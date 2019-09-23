package com.maoshouse.blonk.rest;

import com.google.common.collect.ImmutableList;
import com.maoshouse.blonk.exception.RestApiException;
import lombok.SneakyThrows;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertThrows;

public class BlonkRestApiWrapperTest {

    @DataProvider
    private static Object[] testExecuteHttpsRequestFailure_ClientErrorsDataProvider() {
        return ImmutableList.builder()
                .add(new IOException())
                .add(new InterruptedException())
                .build()
                .toArray();
    }

    @DataProvider
    private static Object[] testExecuteHttpsRequestFailure_BadStatusCodeDataProvider() {
        return ImmutableList.builder()
                .add(HttpURLConnection.HTTP_INTERNAL_ERROR)
                .add(HttpURLConnection.HTTP_SEE_OTHER)
                .add(199)
                .build()
                .toArray();
    }

    @Mock
    private HttpClient httpClient;
    @Mock
    private HttpRequest httpRequest;
    @Mock
    private HttpResponse<String> httpResponse;

    @BeforeMethod
    void beforeMethod() {
        MockitoAnnotations.initMocks(this);
    }

    @SneakyThrows
    @Test
    void testExecuteHttpRequest() {
        setupHttpResponseMocks(HttpURLConnection.HTTP_OK);
        final BlonkRestApiWrapper blonkRestApiWrapper = new BlonkRestApiWrapper(httpClient);
        assertNotNull(blonkRestApiWrapper.executeHttpRequest(httpRequest));
    }

    @SneakyThrows
    @Test(dataProvider = "testExecuteHttpsRequestFailure_ClientErrorsDataProvider")
    void testExecuteHttpsRequestFailure_ClientErrors(Exception exception) {
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenThrow(exception);
        final BlonkRestApiWrapper blonkRestApiWrapper = new BlonkRestApiWrapper(httpClient);
        assertThrows(RestApiException.class, () -> blonkRestApiWrapper.executeHttpRequest(httpRequest));
    }

    @SneakyThrows
    @Test(dataProvider = "testExecuteHttpsRequestFailure_BadStatusCodeDataProvider")
    void testExecuteHttpsRequestFailure_BadStatusCode(int statusCode) {
        setupHttpResponseMocks(statusCode);
        final BlonkRestApiWrapper blonkRestApiWrapper = new BlonkRestApiWrapper(httpClient);
        assertThrows(RestApiException.class, () -> blonkRestApiWrapper.executeHttpRequest(httpRequest));
    }

    @SneakyThrows
    private void setupHttpResponseMocks(int statusCode) {
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);
        when(httpResponse.statusCode()).thenReturn(statusCode);
    }
}
