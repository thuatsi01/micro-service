package com.example.customer.service;

import com.example.customer.model.request.CreateCustomerRequest;
import com.example.customer.model.response.CreateCustomerResponse;
import io.reactivex.Single;

public interface CustomerService {

    Single<CreateCustomerResponse> createCustomerAsync(CreateCustomerRequest request);
}
