package com.odg.spring.spring_core.discount;

import com.odg.spring.spring_core.member.Member;

public interface DiscountPolicy {

    int discount(Member member, int price);
}
