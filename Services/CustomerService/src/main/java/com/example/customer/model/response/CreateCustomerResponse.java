package com.example.customer.model.response;

import com.example.common.model.ResultInformation;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateCustomerResponse {
    ResultInformation result;
}
