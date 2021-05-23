package com.exmaple.order.controller;

import io.reactivex.Single;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("production-support")
public class ProductionSupportApi {

    @GetMapping("/health_check")
    public Single<String> checkAsync() {
        return Single.just("OK");
    }

    @GetMapping("/info")
    public Single<String> infoAsync() {
        return Single.just("OrderAPI version 1.0");
    }
}
