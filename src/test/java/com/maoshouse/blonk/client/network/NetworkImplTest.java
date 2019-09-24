package com.maoshouse.blonk.client.network;

import com.google.common.collect.ImmutableList;
import com.maoshouse.blonk.client.network.model.ArmRequest;
import com.maoshouse.blonk.client.network.model.ArmResponse;
import com.maoshouse.blonk.client.network.model.BlonkNetwork;
import com.maoshouse.blonk.client.network.model.Command;
import com.maoshouse.blonk.client.network.model.DisarmRequest;
import com.maoshouse.blonk.client.network.model.DisarmResponse;
import com.maoshouse.blonk.client.network.model.GetCommandStatusRequest;
import com.maoshouse.blonk.client.network.model.GetCommandStatusResponse;
import com.maoshouse.blonk.client.network.model.ListNetworksRequest;
import com.maoshouse.blonk.client.network.model.ListNetworksResponse;
import com.maoshouse.blonk.constants.BlonkTestConstants;
import com.maoshouse.blonk.rest.BlonkRestApiWrapper;
import com.maoshouse.blonk.rest.ResponseParser;
import com.maoshouse.blonk.rest.ResponseSerdes;
import lombok.SneakyThrows;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class NetworkImplTest {

    private static final String JSON_PLACEHOLDER = "jsonPlaceholder";
    private static final BlonkNetwork BLONK_NETWORK = BlonkNetwork.builder()
            .id("id")
            .name("name")
            .accountId("accountId")
            .armString("armString")
            .build();
    private static final List<BlonkNetwork> BLONK_NETWORKS = ImmutableList.<BlonkNetwork>builder()
            .add(BLONK_NETWORK)
            .build();
    private static final Command COMMAND = new Command("id");

    @Mock
    private BlonkRestApiWrapper blonkRestApiWrapper;
    @Mock
    private ResponseParser responseParser;
    @Mock
    private ResponseSerdes responseSerdes;
    @Mock
    private HttpResponse httpResponse;

    @BeforeMethod
    void beforeMethod() {
        MockitoAnnotations.initMocks(this);
    }

    @SneakyThrows
    @Test
    void testListNetworks() {
        when(blonkRestApiWrapper.executeHttpRequest(any(HttpRequest.class))).thenReturn(httpResponse);
        when(responseParser.getAsStringIfExists(anyString(), eq(httpResponse))).thenReturn(JSON_PLACEHOLDER);
        when(responseSerdes.fromJson(anyString(), any())).thenReturn(BLONK_NETWORKS);
        final Network network = new NetworkImpl(BlonkTestConstants.AUTH_TOKEN, blonkRestApiWrapper, responseParser, responseSerdes);
        ListNetworksResponse listNetworksResponse = network.listNetworks(new ListNetworksRequest());
        assertEquals(listNetworksResponse.getBlonkNetworks(), BLONK_NETWORKS);
    }

    @SneakyThrows
    @Test
    void testArm() {
        when(blonkRestApiWrapper.executeHttpRequest(any(HttpRequest.class))).thenReturn(httpResponse);
        when(responseParser.getAsStringIfExists(anyString(), eq(httpResponse))).thenReturn(JSON_PLACEHOLDER);
        final Network network = new NetworkImpl(BlonkTestConstants.AUTH_TOKEN, blonkRestApiWrapper, responseParser, responseSerdes);
        ArmResponse armResponse = network.arm(new ArmRequest(BLONK_NETWORK));
        assertEquals(armResponse.getCommand().getId(), JSON_PLACEHOLDER);
    }

    @SneakyThrows
    @Test
    void testDisarm() {
        when(blonkRestApiWrapper.executeHttpRequest(any(HttpRequest.class))).thenReturn(httpResponse);
        when(responseParser.getAsStringIfExists(anyString(), eq(httpResponse))).thenReturn(JSON_PLACEHOLDER);
        final Network network = new NetworkImpl(BlonkTestConstants.AUTH_TOKEN, blonkRestApiWrapper, responseParser, responseSerdes);
        DisarmResponse disarmResponse = network.disArm(new DisarmRequest(BLONK_NETWORK));
        assertEquals(disarmResponse.getCommand().getId(), JSON_PLACEHOLDER);
    }

    @SneakyThrows
    @Test
    void testGetCommandStatus() {
        when(blonkRestApiWrapper.executeHttpRequest(any(HttpRequest.class))).thenReturn(httpResponse);
        when(responseParser.getAsStringIfExists(anyString(), eq(httpResponse))).thenReturn(Boolean.TRUE.toString());
        final Network network = new NetworkImpl(BlonkTestConstants.AUTH_TOKEN, blonkRestApiWrapper, responseParser, responseSerdes);
        GetCommandStatusResponse getCommandStatusResponse = network.getCommandStatus(new GetCommandStatusRequest(BLONK_NETWORK, COMMAND));
        assertEquals(getCommandStatusResponse.getCommandStatus().isComplete(), Boolean.TRUE.booleanValue());
    }
}
