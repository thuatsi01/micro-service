package com.example.shippingservice.service;

import com.example.shippingservice.model.request.CreateShipperRequest;
import com.example.shippingservice.model.response.CreateShipperResponse;
import io.reactivex.Single;

public interface ShipperService {

    Single<CreateShipperResponse> createShipperAsync(CreateShipperRequest request);
}
