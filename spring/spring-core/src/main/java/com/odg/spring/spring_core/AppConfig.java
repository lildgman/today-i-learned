package com.odg.spring.spring_core;

import com.odg.spring.spring_core.discount.DiscountPolicy;
import com.odg.spring.spring_core.discount.FixDiscountPolicy;
import com.odg.spring.spring_core.discount.RateDiscountPolicy;
import com.odg.spring.spring_core.member.MemberRepository;
import com.odg.spring.spring_core.member.MemberService;
import com.odg.spring.spring_core.member.MemberServiceImpl;
import com.odg.spring.spring_core.member.MemoryMemberRepository;
import com.odg.spring.spring_core.order.OrderService;
import com.odg.spring.spring_core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");

        return new OrderServiceImpl(
                memberRepository(),
                discountPolicy());
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");

        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
