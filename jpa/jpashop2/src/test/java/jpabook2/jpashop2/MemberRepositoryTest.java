package jpabook2.jpashop2;

import jpabook2.jpashop2.domain.Member;
import jpabook2.jpashop2.repository.MemberRepositoryOld;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepositoryOld memberRepositoryOld;

    @Test
    @Transactional
//    @Rollback(false)
    public void testMember() {

        // given
        Member member = new Member();
        member.setUsername("memberA");

        // when
//        Long savedId = memberRepository.save(member);
//        Member findMember = memberRepository.find(savedId);
//
//        // then
//        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
//        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
//        Assertions.assertThat(findMember).isEqualTo(member);
    }


}