package com.maoshouse.blonk.rest;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonParser;
import com.maoshouse.blonk.exception.NotFoundException;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.http.HttpResponse;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

public class ResponseParserTest {

    private static final String MEMBER = "member";
    private static final String MEMBER_STRING_VALUE = "memberValue";
    private static final String JSON_STRING_MEMBER_VALUE = "\"memberValue\"";
    private static final String NON_EXISTENT_MEMBER = "nonExistentMember";
    private static final String RESPONSE_OBJECT_JSON_STRING_FORMAT = "{" +
            "\"" + MEMBER + "\" : %s" +
            "}";

    @DataProvider
    private static final Object[] testGetAsStringIfExistsDataProvider() {
        return ImmutableList.builder()
                .add("true")
                .add("false")
                .add("1")
                .add("1.1")
                .add("\"memberValue\"")
                .add("{\"key\":\"value\"}")
                .add("[1,2,3,4]")
                .build()
                .toArray();
    }

    @Mock
    private HttpResponse<String> httpResponse;

    @SneakyThrows
    @BeforeMethod
    void beforeMethod() {
        MockitoAnnotations.initMocks(this);
    }

    @SneakyThrows
    @Test(dataProvider = "testGetAsStringIfExistsDataProvider")
    void testGetAsStringIfExists(String memberValue) {
        when(httpResponse.body()).thenReturn(String.format(RESPONSE_OBJECT_JSON_STRING_FORMAT, memberValue));
        String.format(RESPONSE_OBJECT_JSON_STRING_FORMAT, memberValue);
        final ResponseParser responseParser = new ResponseParser(new JsonParser());
        String expectedString = memberValue;
        if (StringUtils.equals(memberValue, JSON_STRING_MEMBER_VALUE)) {
            expectedString = MEMBER_STRING_VALUE;
        }
        assertEquals(responseParser.getAsStringIfExists(MEMBER, httpResponse), expectedString);
    }

    @SneakyThrows
    @Test
    void testGetAsStringIfExists_MemberDoesNotExist() {
        when(httpResponse.body()).thenReturn(String.format(RESPONSE_OBJECT_JSON_STRING_FORMAT, MEMBER_STRING_VALUE));
        final ResponseParser responseParser = new ResponseParser(new JsonParser());
        assertThrows(NotFoundException.class, () -> responseParser.getAsStringIfExists(NON_EXISTENT_MEMBER, httpResponse));
    }
}
