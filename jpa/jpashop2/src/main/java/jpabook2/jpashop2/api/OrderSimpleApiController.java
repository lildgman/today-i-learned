package jpabook2.jpashop2.api;

import jpabook2.jpashop2.domain.Address;
import jpabook2.jpashop2.domain.Order;
import jpabook2.jpashop2.domain.OrderSearch;
import jpabook2.jpashop2.domain.OrderStatus;
import jpabook2.jpashop2.repository.OrderRepository;
import jpabook2.jpashop2.repository.SimpleOrderQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1() {
        List<Order> allByString = orderRepository.findAllByString(new OrderSearch());
        for (Order order : allByString) {
            order.getMember().getUsername(); // Lazy 강제 초기화
            order.getDelivery().getAddress(); // Lazy 강제 초기화
        }
        return allByString;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {

        // ORDER 2개 조회
        // 1회 조회
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());

        // ORDER 개수만큼 반복
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());

        // 쿼리가 총 1 + N + N번 실행
        // order 조회 1번
        // order -> member 지연 로딩 조회 N번
        // order -> delivery 지연 로딩 조회 N번

        return result;

    }

    // 엔티티로 조회해서 DTO로 변환 (fetch join 사용)
    @GetMapping("api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();

        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());

        return result;
    }

    // JPA에서 DTO로 바로 조회
    @GetMapping("api/v4/simple-orders")
    public List<SimpleOrderQueryDto> ordersV4() {
        return orderRepository.findOrderDtos();
    }

    /**
     * 쿼리 방식 선택 권장 순서
     * 1. 엔티티를 DTO로 변환하는 방법 선택
     * 2. 필요하면 페치 조인으로 성능을 최적화 -> 여기서 대부분의 이슈가 해결
     * 3. 그래도 안될 시 DTO로 직접 조회
     * 4. 최후의 방법으로 JPA가 제공하는 네이티브 SQL이나 스프링 JDBC Template을 사용해 SQL을 직접 사용
     */

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getUsername();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }
}
