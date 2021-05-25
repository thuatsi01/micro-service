package com.example.customer.controller;

import com.example.customer.model.request.CreateCustomerRequest;
import com.example.customer.model.response.CreateCustomerResponse;
import com.example.customer.service.CustomerService;
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
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public Single<ResponseEntity<CreateCustomerResponse>> createCustomerAsync(@Valid @RequestBody CreateCustomerRequest request) {
        return customerService.createCustomerAsync(request)
                .map(ResponseEntity::ok);
    }
}
