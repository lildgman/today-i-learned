package com.odg.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void save() {
        // given
        Member member = new Member("Oh", 20);
        // when
        Member savedMember = memberRepository.save(member);
        //then
        Member findMember = memberRepository.findById(member.getId());
        Assertions.assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    void findAll() {
        // given
        Member member1 = new Member("kim", 20);
        Member member2 = new Member("Kang", 30);

        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> allResult = memberRepository.findAll();

        // then
        Assertions.assertThat(allResult.size()).isEqualTo(2);
        Assertions.assertThat(allResult).contains(member1, member2);


    }
}