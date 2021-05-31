package com.example.customer.model.response;

import com.example.common.model.ResultInformation;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductInfoResponse {
    @JsonProperty("result")
    ResultInformation result;

    @JsonProperty("user_id")
    Integer userId;

    @JsonProperty("information")
    String information;
}
