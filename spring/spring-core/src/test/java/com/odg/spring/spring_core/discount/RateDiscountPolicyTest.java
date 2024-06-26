package com.odg.spring.spring_core.discount;

import com.odg.spring.spring_core.member.Grade;
import com.odg.spring.spring_core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest {

    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP는 할인 10퍼 적용")
    void yesVip() {
        // given
        Member member = new Member(1L, "userA", Grade.VIP);
        // when
        int discount = discountPolicy.discount(member, 10000);
        // then
        Assertions.assertThat(discount).isEqualTo(1000);
    }

    @Test
    @DisplayName("VIP가 아니면 할인 미적용")
    void noVip() {
        // given
        Member member = new Member(2L, "userB", Grade.BASIC);
        // when
        int discount = discountPolicy.discount(member, 20000);
        // then
        Assertions.assertThat(discount).isEqualTo(0);

    }
}