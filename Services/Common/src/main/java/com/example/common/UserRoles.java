package com.example.common;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserRoles {
    CUSTOMER(1, "CUSTOMER"),
    SHIPPER(2, "SHIPPER");

    private final Integer id;
    private final String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
