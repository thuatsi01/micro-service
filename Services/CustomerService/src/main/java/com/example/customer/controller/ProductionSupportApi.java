package com.example.customer.controller;

import com.example.common.model.ResultInformation;
import com.example.customer.model.response.ProductInfoResponse;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("production-support")
public class ProductionSupportApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductionSupportApi.class);

    @GetMapping("/health_check")
    public Single<String> checkAsync() {
        return Single.just("OK");
    }

    @GetMapping("/info")
    public Single<ResponseEntity<ProductInfoResponse>> infoAsync(@RequestHeader("user_id") @NotNull Integer userId) {
        LOGGER.info("User's reviewing information service with id: [{}]", userId);
        return Single.just(ProductInfoResponse.builder()
                .result(ResultInformation.ok())
                .information("CustomerAPI version 1.0")
                .userId(userId)
                .build()
        ).map(ResponseEntity::ok);
    }
}
