package com.spring.boot.shop.repository;

import com.spring.boot.shop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;

}
