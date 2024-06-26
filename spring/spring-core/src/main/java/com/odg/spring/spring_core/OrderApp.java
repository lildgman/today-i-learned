package com.odg.spring.spring_core;

import com.odg.spring.spring_core.member.Grade;
import com.odg.spring.spring_core.member.Member;
import com.odg.spring.spring_core.member.MemberService;
import com.odg.spring.spring_core.member.MemberServiceImpl;
import com.odg.spring.spring_core.order.Order;
import com.odg.spring.spring_core.order.OrderService;
import com.odg.spring.spring_core.order.OrderServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {

    public static void main(String[] args) {

//        AppConfig appConfig = new AppConfig();
//
//        MemberService memberService = appConfig.memberService();
//        OrderService orderService = appConfig.orderService();

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);


        long memberId = 1L;
        Member member = new Member(memberId, "user1", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "item1", 10000);

        System.out.println("order = " + order);


    }
}
