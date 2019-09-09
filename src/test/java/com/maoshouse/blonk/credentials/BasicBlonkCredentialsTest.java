package com.maoshouse.blonk.credentials;

import com.maoshouse.blonk.constants.LoginConstants;
import com.maoshouse.blonk.rest.BlonkRestApiWrapper;
import lombok.SneakyThrows;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class BasicBlonkCredentialsTest {

    @Mock
    private BlonkRestApiWrapper blonkRestApiWrapper;

    @BeforeMethod
    void beforeMethod() {
        MockitoAnnotations.initMocks(this);
    }

    @SneakyThrows
    @Test
    void testGetAuthToken() {
        when(blonkRestApiWrapper.login(eq(LoginConstants.USER_NAME), eq(LoginConstants.PASSWORD))).thenReturn(LoginConstants.AUTH_TOKEN);
        BasicBlonkCredentials basicBlonkCredentials = new BasicBlonkCredentials(blonkRestApiWrapper, LoginConstants.USER_NAME, LoginConstants.PASSWORD);
        assertEquals(basicBlonkCredentials.getAuthToken(), LoginConstants.AUTH_TOKEN);
    }
}
