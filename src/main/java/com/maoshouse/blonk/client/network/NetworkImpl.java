package com.maoshouse.blonk.client.network;

import com.google.common.base.Preconditions;
import com.maoshouse.blonk.client.BlonkAuthenticatedClient;
import com.maoshouse.blonk.client.network.model.ArmRequest;
import com.maoshouse.blonk.client.network.model.ArmResponse;
import com.maoshouse.blonk.client.network.model.BlonkNetwork;
import com.maoshouse.blonk.client.network.model.Command;
import com.maoshouse.blonk.client.network.model.CommandStatus;
import com.maoshouse.blonk.client.network.model.DisarmRequest;
import com.maoshouse.blonk.client.network.model.DisarmResponse;
import com.maoshouse.blonk.client.network.model.GetCommandStatusRequest;
import com.maoshouse.blonk.client.network.model.GetCommandStatusResponse;
import com.maoshouse.blonk.client.network.model.ListNetworksRequest;
import com.maoshouse.blonk.client.network.model.ListNetworksResponse;
import com.maoshouse.blonk.exception.NotFoundException;
import com.maoshouse.blonk.exception.ResponseParsingException;
import com.maoshouse.blonk.exception.RestApiException;
import com.maoshouse.blonk.model.AuthToken;
import com.maoshouse.blonk.rest.BlonkEndpoint;
import com.maoshouse.blonk.rest.BlonkHttpRequest;
import com.maoshouse.blonk.rest.BlonkRestApiWrapper;
import com.maoshouse.blonk.rest.ResponseParser;
import com.maoshouse.blonk.rest.ResponseSerdes;
import lombok.NonNull;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.List;

public class NetworkImpl extends BlonkAuthenticatedClient implements Network {

    private static final String ARM_ENDPOINT_FORMAT = "network/%s/arm";
    private static final String DISARM_ENDPOINT_FORMAT = "network/%s/disarm";
    private static final String GET_COMMAND_STATUS_ENDPOINT_FORMAT = "network/%s/command/%s";
    private static final String NETWORKS_MEMBER_NAME = "networks";
    private static final String COMMAND_ID_MEMBER_NAME = "id";
    private static final String COMMAND_STATUS_COMPLETE_MEMBER_NAME = "complete";

    @NonNull
    private BlonkRestApiWrapper blonkRestApiWrapper;
    @NonNull
    private ResponseParser responseParser;
    @NonNull
    private ResponseSerdes responseSerdes;

    public NetworkImpl(@NonNull final AuthToken authToken, final BlonkRestApiWrapper blonkRestApiWrapper,
                       final ResponseParser responseParser, final ResponseSerdes responseSerdes) {
        super(authToken);
        this.blonkRestApiWrapper = Preconditions.checkNotNull(blonkRestApiWrapper);
        this.responseParser = Preconditions.checkNotNull(responseParser);
        this.responseSerdes = Preconditions.checkNotNull(responseSerdes);
    }

    @Override
    public ListNetworksResponse listNetworks(final ListNetworksRequest request) throws RestApiException,
            NotFoundException, ResponseParsingException {
        Preconditions.checkNotNull(request);

        HttpResponse httpResponse =
                blonkRestApiWrapper.executeHttpRequest(BlonkHttpRequest.get(BlonkEndpoint.NETWORKS_API_ENDPOINT)
                        .withAuthToken(getAuthToken())
                        .build());
        String networksJsonArray = responseParser.getAsStringIfExists(NETWORKS_MEMBER_NAME, httpResponse);
        List<BlonkNetwork> blonkNetworks = responseSerdes.fromJson(networksJsonArray,
                ResponseSerdes.LIST_OF_BLONK_NETWORKS_TYPE);
        return new ListNetworksResponse(blonkNetworks);
    }

    @Override
    public ArmResponse arm(final ArmRequest request) throws RestApiException, NotFoundException,
            ResponseParsingException {
        Preconditions.checkNotNull(request);

        String endpoint = String.format(ARM_ENDPOINT_FORMAT, request.getBlonkNetwork().getId());
        return new ArmResponse(executeNetworkCommand(BlonkEndpoint.createApiEndpoint(endpoint)));
    }

    @Override
    public DisarmResponse disArm(final DisarmRequest request) throws RestApiException, NotFoundException,
            ResponseParsingException {
        Preconditions.checkNotNull(request);

        String endpoint = String.format(DISARM_ENDPOINT_FORMAT, request.getBlonkNetwork().getId());
        return new DisarmResponse(executeNetworkCommand(BlonkEndpoint.createApiEndpoint(endpoint)));
    }

    public GetCommandStatusResponse getCommandStatus(final GetCommandStatusRequest request) throws RestApiException,
            NotFoundException, ResponseParsingException {
        Preconditions.checkNotNull(request);

        String endpoint = String.format(GET_COMMAND_STATUS_ENDPOINT_FORMAT, request.getBlonkNetwork().getId(),
                request.getCommand().getId());
        HttpResponse httpResponse =
                blonkRestApiWrapper.executeHttpRequest(BlonkHttpRequest.get(BlonkEndpoint.createApiEndpoint(endpoint))
                        .withAuthToken(getAuthToken())
                        .build());
        String complete = responseParser.getAsStringIfExists(COMMAND_STATUS_COMPLETE_MEMBER_NAME, httpResponse);
        return new GetCommandStatusResponse(new CommandStatus(Boolean.parseBoolean(complete)));
    }

    private Command executeNetworkCommand(final URI endpoint) throws RestApiException, NotFoundException,
            ResponseParsingException {
        HttpResponse httpResponse = blonkRestApiWrapper.executeHttpRequest(BlonkHttpRequest.post(endpoint, "{}")
                .withAuthToken(getAuthToken())
                .build());
        String commandId = responseParser.getAsStringIfExists(COMMAND_ID_MEMBER_NAME, httpResponse);
        return new Command(commandId);
    }
}
