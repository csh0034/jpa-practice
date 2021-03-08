package com.spring.boot.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }
}
