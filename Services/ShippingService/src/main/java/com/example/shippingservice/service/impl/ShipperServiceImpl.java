package com.example.shippingservice.service.impl;

import com.example.common.model.ResultInformation;
import com.example.shippingservice.entities.Shipper;
import com.example.shippingservice.grpc.AuthGrpcService;
import com.example.shippingservice.model.request.CreateShipperRequest;
import com.example.shippingservice.model.response.CreateShipperResponse;
import com.example.shippingservice.repository.ShipperRepository;
import com.example.shippingservice.service.ShipperService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ShipperServiceImpl implements ShipperService {

    @Autowired
    private ShipperRepository shipperRepository;

    @Autowired
    private AuthGrpcService authGrpcService;

    @Override
    public Single<CreateShipperResponse> createShipperAsync(CreateShipperRequest request) {
        return authGrpcService.createUserAsync(request)
                .flatMap(createUserResponse ->
                        Single.fromCallable(() -> {
                            var shipper = new Shipper();
                            shipper.setId(createUserResponse.getUserId());
                            shipper.setLocation(request.getLocation());
                            shipper.setAddress(request.getAddress());
                            shipper.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                            return shipperRepository.save(shipper);
                        }).subscribeOn(Schedulers.io())
                ).observeOn(Schedulers.computation())
                .map(customer ->
                        CreateShipperResponse.builder()
                                .result(ResultInformation.ok())
                                .build()
                );
    }
}
