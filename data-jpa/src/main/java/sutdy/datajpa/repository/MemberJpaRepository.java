package sutdy.datajpa.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sutdy.datajpa.entity.Member;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepository {

    private final EntityManager em;

    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}