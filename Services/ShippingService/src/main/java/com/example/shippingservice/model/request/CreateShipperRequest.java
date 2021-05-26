package com.example.shippingservice.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateShipperRequest {

    @NotNull
    @Size(min = 9, max = 11)
    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("location")
    private String location;

    @JsonProperty("address")
    private String address;

    @NotNull
    @Size(min = 6)
    @JsonProperty("password")
    private String password;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
