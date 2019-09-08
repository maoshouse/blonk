package com.maoshouse.blonk.rest;

import com.maoshouse.blonk.constants.LoginConstants;
import lombok.SneakyThrows;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.maoshouse.blonk.rest.BlonkRestApiWrapper.AUTH_TOKEN_MEMBER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class BlonkRestApiWrapperTest {

    @Mock
    private HttpClient httpClient;
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
        setupHttpResponseMocks();
        when(responseParser.getAsStringIfExists(AUTH_TOKEN_MEMBER, httpResponse)).thenReturn(LoginConstants.AUTH_TOKEN.toJson());
        final BlonkRestApiWrapper blonkRestApiWrapper = new BlonkRestApiWrapper(httpClient, responseParser);
        assertEquals(blonkRestApiWrapper.login(LoginConstants.USER_NAME, LoginConstants.PASSWORD), LoginConstants.AUTH_TOKEN);
    }

    @SneakyThrows
    private void setupHttpResponseMocks() {
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);
        when(httpResponse.statusCode()).thenReturn(HttpURLConnection.HTTP_OK);
    }
}
