package jpabook2.jpashop2.api;

import jpabook2.jpashop2.domain.Order;
import jpabook2.jpashop2.domain.OrderItem;
import jpabook2.jpashop2.domain.OrderSearch;
import jpabook2.jpashop2.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    @GetMapping("api/v1/orders")
    public List<Order> ordersV1() {

        List<Order> orders = orderRepository.findAllByString(new OrderSearch());

        for (Order order : orders) {
            order.getMember().getUsername();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }

        return orders;
    }
}
