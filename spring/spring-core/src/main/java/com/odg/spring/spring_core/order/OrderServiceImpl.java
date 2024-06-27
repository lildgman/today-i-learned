package com.odg.spring.spring_core.order;

import com.odg.spring.spring_core.annotation.MainDiscountPolicy;
import com.odg.spring.spring_core.discount.DiscountPolicy;
import com.odg.spring.spring_core.discount.FixDiscountPolicy;
import com.odg.spring.spring_core.discount.RateDiscountPolicy;
import com.odg.spring.spring_core.member.Member;
import com.odg.spring.spring_core.member.MemberRepository;
import com.odg.spring.spring_core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository,
                            @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId,itemName,itemPrice,discountPrice);
    }
}
