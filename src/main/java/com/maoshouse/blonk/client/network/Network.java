package com.maoshouse.blonk.client.network;

import com.maoshouse.blonk.client.network.model.ArmRequest;
import com.maoshouse.blonk.client.network.model.ArmResponse;
import com.maoshouse.blonk.client.network.model.DisarmRequest;
import com.maoshouse.blonk.client.network.model.DisarmResponse;
import com.maoshouse.blonk.client.network.model.GetCommandStatusRequest;
import com.maoshouse.blonk.client.network.model.GetCommandStatusResponse;
import com.maoshouse.blonk.client.network.model.ListNetworksRequest;
import com.maoshouse.blonk.client.network.model.ListNetworksResponse;
import com.maoshouse.blonk.exception.NotFoundException;
import com.maoshouse.blonk.exception.ResponseParsingException;
import com.maoshouse.blonk.exception.RestApiException;

public interface Network {

    ListNetworksResponse listNetworks(final ListNetworksRequest request) throws RestApiException, NotFoundException, ResponseParsingException;

    ArmResponse arm(final ArmRequest request) throws RestApiException, NotFoundException, ResponseParsingException;

    DisarmResponse disArm(final DisarmRequest request) throws RestApiException, NotFoundException, ResponseParsingException;

    GetCommandStatusResponse getCommandStatus(final GetCommandStatusRequest request) throws RestApiException, NotFoundException, ResponseParsingException;
}
