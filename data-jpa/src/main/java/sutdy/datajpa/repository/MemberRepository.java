package sutdy.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sutdy.datajpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
