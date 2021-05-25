package com.example.customer.service.impl;

import com.example.common.model.ResultInformation;
import com.example.customer.entities.Customer;
import com.example.customer.grpc.AuthGrpcService;
import com.example.customer.model.request.CreateCustomerRequest;
import com.example.customer.model.response.CreateCustomerResponse;
import com.example.customer.repository.CustomerRepository;
import com.example.customer.service.CustomerService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AuthGrpcService authGrpcService;

    @Override
    public Single<CreateCustomerResponse> createCustomerAsync(CreateCustomerRequest request) {
        return authGrpcService.createUserAsync(request)
                .flatMap(createUserResponse ->
                        Single.fromCallable(() -> {
                            var customer = new Customer();
                            customer.setId(createUserResponse.getUserId());
                            customer.setPhoneNumber(request.getPhoneNumber());
                            customer.setAddress(request.getAddress());
                            return customerRepository.save(customer);
                        }).subscribeOn(Schedulers.io())
                ).observeOn(Schedulers.computation())
                .map(customer ->
                        CreateCustomerResponse.builder()
                                .result(ResultInformation.ok())
                                .build()
                );
    }
}
