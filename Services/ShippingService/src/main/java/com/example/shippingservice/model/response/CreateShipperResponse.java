package com.example.shippingservice.model.response;

import com.example.common.model.ResultInformation;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateShipperResponse {
    ResultInformation result;
}
