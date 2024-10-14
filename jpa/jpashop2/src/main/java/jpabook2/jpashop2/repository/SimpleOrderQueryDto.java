package jpabook2.jpashop2.repository;

import jpabook2.jpashop2.domain.Address;
import jpabook2.jpashop2.domain.Order;
import jpabook2.jpashop2.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimpleOrderQueryDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public SimpleOrderQueryDto(Long orderId, String username, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = username;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}