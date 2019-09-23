package com.maoshouse.blonk.credentials;

import com.maoshouse.blonk.client.BlonkClient;
import com.maoshouse.blonk.constants.BlonkTestConstants;
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
    private BlonkClient blonkClient;

    @BeforeMethod
    void beforeMethod() {
        MockitoAnnotations.initMocks(this);
    }

    @SneakyThrows
    @Test
    void testGetAuthToken() {
        when(blonkClient.login(eq(BlonkTestConstants.USER_NAME), eq(BlonkTestConstants.PASSWORD))).thenReturn(BlonkTestConstants.AUTH_TOKEN);
        BasicBlonkCredentials basicBlonkCredentials = new BasicBlonkCredentials(blonkClient, BlonkTestConstants.USER_NAME, BlonkTestConstants.PASSWORD);
        assertEquals(basicBlonkCredentials.getAuthToken(), BlonkTestConstants.AUTH_TOKEN);
    }
}
