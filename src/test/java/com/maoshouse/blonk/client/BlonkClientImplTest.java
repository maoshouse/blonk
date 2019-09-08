package com.maoshouse.blonk.client;

import com.maoshouse.blonk.constants.LoginConstants;
import com.maoshouse.blonk.model.LoginRequest;
import com.maoshouse.blonk.model.LoginResponse;
import com.maoshouse.blonk.rest.BlonkRestApiWrapper;
import lombok.SneakyThrows;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class BlonkClientImplTest {

    private static final LoginRequest LOGIN_REQUEST = LoginRequest.builder()
            .username(LoginConstants.USER_NAME)
            .password(LoginConstants.PASSWORD)
            .build();
    private static final LoginResponse LOGIN_RESPONSE = LoginResponse.builder()
            .authToken(LoginConstants.AUTH_TOKEN)
            .build();
    @Mock
    private BlonkRestApiWrapper blonkRestApiWrapper;

    @BeforeMethod
    void beforeMethod() {
        MockitoAnnotations.initMocks(this);
    }

    @SneakyThrows
    @Test
    void testLogin() {
        when(blonkRestApiWrapper.login(eq(LoginConstants.USER_NAME), eq(LoginConstants.PASSWORD))).thenReturn(LoginConstants.AUTH_TOKEN);
        final BlonkClient blonkClient = new BlonkClientImpl(blonkRestApiWrapper);
        assertEquals(blonkClient.login(LOGIN_REQUEST), LOGIN_RESPONSE);
    }
}
