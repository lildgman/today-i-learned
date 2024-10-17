package jpabook2.jpashop2.api;

import jpabook2.jpashop2.domain.*;
import jpabook2.jpashop2.repository.OrderRepository;
import jpabook2.jpashop2.repository.order.query.OrderFlatDto;
import jpabook2.jpashop2.repository.order.query.OrderItemQueryDto;
import jpabook2.jpashop2.repository.order.query.OrderQueryDto;
import jpabook2.jpashop2.repository.order.query.OrderQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

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

    @GetMapping("api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());

        return result;
    }
    // v2로 조회 시 SQL 실행 횟수
    // order 1번
    // member N번, address N번 (order의 횟수만큼)
    // orderItem N번 (order 횟수만큼)
    // item N번 (orderItem 횟수만큼)

    @GetMapping("api/v3/orders")
    public List<OrderDto> orderV3() {
        List<Order> orders = orderRepository.findAllWithItem();

        for (Order order : orders) {
            System.out.println("order = " + order);
            System.out.println("order.getId() = " + order.getId());
        }

        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());

        return result;
    }

    // 페이징 + 컬렉션 엔티티 조회
    // ToOne관계는 모두 페치조인 -> ToOne관계는 row수를 증가시키지 않는다. -> 페이징의 영향을 주지 않음
    // 컬렉션은 지연로딩으로 조회
    // 지연로딩 성능 최적화를 위해
    // hibernate.default_batch_fetch_size, @BatchSize 적용
    // hibernate.default_batch_fetch_size: 글로벌 설정
    // @BatchSize: 개별 설정, 컬렉션은 컬렉션 필드에, 엔티티는 엔티티 클래스에 적용하면 된다.
    // 이 옵션을 사용하면 설정한 크기만큼 프록시 객체를 인쿼리를 사용하여 조회

    // 장점
    // 쿼리 호출 수가 1 + N 에서 1 + 1로 최적화
    // 조인보다 DB 데이터 전송량이 최적화된다
    // 컬렉션 페치 조인은 페이징이 불가능하지만 이 방법은 가능하

    // 100 ~ 1000 사이로 사이즈를 조절하자
    // 데이터베이스마다 IN절 파라미터를 1000으로 제한하기도 하고 1000으로 잡으면 한번에 1000를 DB에서 애플리케이에 불러오므로 DB에 순간적인 부하가 증가할 수도 있다.
    // 그리고 100으로하나 1000으로하나 불러올 데이터의 양은 똑같기 때문에 메모리 사용량은 똑같다.
    @GetMapping("api/v3.1/orders")
    public List<OrderDto> orderV3_page(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit) {
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);

        for (Order order : orders) {
            System.out.println("order = " + order);
            System.out.println("order.getId() = " + order.getId());
        }

        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());

        return result;
    }

    @GetMapping("api/v4/orders")
    public List<OrderQueryDto> ordersV4() {
        return orderQueryRepository.findOrderQueryDtos();
    }

    @GetMapping("api/v5/orders")
    public List<OrderQueryDto> ordersV5() {
        return orderQueryRepository.findAllByDto_optimization();
    }

    @GetMapping("api/v6/orders")
    public List<OrderQueryDto> ordersV6() {
        List<OrderFlatDto> flats = orderQueryRepository.findAllByDto_flat();


        return flats.stream()
                .collect(groupingBy(o -> new OrderQueryDto(o.getOrderId(),
                                o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                        mapping(o -> new OrderItemQueryDto(o.getOrderId(),
                                o.getItemName(), o.getOrderPrice(), o.getCount()), toList())))
                .entrySet().stream()
                .map(e -> new OrderQueryDto(e.getKey().getOrderId(),
                        e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(),
                        e.getKey().getAddress(), e.getValue()))
                .collect(toList());
    }

    // OrderDto로 OrderItems 조회 시 OrderItems 엔티티를 조회하는 것이 아닌 Dto로 조회
    @Data
    static class OrderDto {

        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;

        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getUsername();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            orderItems = order.getOrderItems().stream()
                    .map(orderItem -> new OrderItemDto(orderItem))
                    .collect(toList());
        }
    }

    @Data
    static class OrderItemDto {

        private String itemName;
        private int orderPrice;
        private int count;

        public OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }
}
