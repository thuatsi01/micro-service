package com.example.shippingservice.controller;

import com.example.shippingservice.model.request.CreateShipperRequest;
import com.example.shippingservice.model.response.CreateShipperResponse;
import com.example.shippingservice.service.ShipperService;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/customer")
public class ShipperController {

    @Autowired
    private ShipperService shipperService;

    @PostMapping("/register")
    public Single<ResponseEntity<CreateShipperResponse>> createCustomerAsync(@Valid @RequestBody CreateShipperRequest request) {
        return shipperService.createShipperAsync(request)
                .map(ResponseEntity::ok);
    }
}
