package com.maoshouse.blonk.client;

import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;

public class BlonkClientImplTest {

    @BeforeMethod
    void beforeMethod() {
        MockitoAnnotations.initMocks(this);
    }
}
