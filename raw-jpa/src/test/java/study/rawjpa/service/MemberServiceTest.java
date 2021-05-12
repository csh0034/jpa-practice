package study.rawjpa.service;

import study.rawjpa.shop.domain.Member;
import study.rawjpa.shop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.rawjpa.shop.service.MemberService;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    public void join() {
        // GIVEN
        Member member = new Member();
        member.setName("choi");

        // WHEN
        Long savedId = memberService.join(member);

        // THEN
        em.flush();
        assertThat(member).isEqualTo(memberRepository.findOne(savedId));
    }

    @Test
    public void throw_duplicate_member() {
        //GIVEN
        Member member1 = new Member();
        member1.setName("choi");

        Member member2 = new Member();
        member2.setName("choi");

        //WHEN
        memberService.join(member1);

        //THEN
        assertThatIllegalStateException().isThrownBy(() -> memberService.join(member2));
    }
}