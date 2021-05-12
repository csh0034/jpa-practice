package study.rawjpa.shop.repository;

import lombok.Getter;
import lombok.Setter;
import study.rawjpa.shop.domain.OrderStatus;

@Getter
@Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;
}