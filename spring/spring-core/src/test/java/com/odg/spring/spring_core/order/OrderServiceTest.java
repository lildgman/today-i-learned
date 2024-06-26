package com.odg.spring.spring_core.order;

import com.odg.spring.spring_core.AppConfig;
import com.odg.spring.spring_core.member.Grade;
import com.odg.spring.spring_core.member.Member;
import com.odg.spring.spring_core.member.MemberService;
import com.odg.spring.spring_core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderServiceTest {

    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    void BeforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }

    @Test
    void createOrder() {
        long memberId = 1L;
        Member member = new Member(memberId, "user1", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "item1", 10000);

        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}