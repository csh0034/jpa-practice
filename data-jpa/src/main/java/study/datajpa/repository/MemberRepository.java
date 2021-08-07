package study.datajpa.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

  List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

  @Query("select m from Member m where m.username= :username and m.age = :age")
  List<Member> findUser(@Param("username") String username, @Param("age") int age);

  @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
  List<MemberDto> findMemberDto();

  @Query("select m from Member m where m.username in :names")
  List<Member> findByNames(@Param("names") Collection<String> names);

  // 다양한 리턴타입 예제
  List<Member> findListByUsername(String name); //컬렉션
  Member findMemberByUsername(String name); //단건
  Optional<Member> findOptionalByUsername(String name); //단건 Optional

  // Paging
  Page<Member> findByAge(int age, Pageable pageable);
  Slice<Member> findSliceByAge(int age, Pageable pageable);

  @Query(value = "select m from Member m left join  m.team", countQuery = "select count(m.username) from Member m")
  Page<Member> findMembers(Pageable pageable);  // count 쿼리 분리 가능

  // 벌크 update
  @Modifying(clearAutomatically = true) // 벌크 연산 이후 em.clear() 호출
  @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
  int bulkAgePlus(@Param("age") int age);

  @Query("select m from Member m left join fetch m.team")
  List<Member> findMemberFetchJoin();

  // EntityGraph
  //공통 메서드 오버라이드
  @Override
  @EntityGraph(attributePaths = {"team"})
  List<Member> findAll();

  //JPQL + 엔티티 그래프
  @EntityGraph(attributePaths = {"team"})
  @Query("select m from Member m")
  List<Member> findMemberEntityGraph();

  //메서드 이름으로 쿼리에서 특히 편리함
  @EntityGraph(attributePaths = {"team"})
  List<Member> findByUsername(String username);

  // @NamedEntityGraph 와 사용
  @EntityGraph("Member.all")
  List<Member> findEntityGraphByUsername(String username);

  // JPA QueryHint
  @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
  Member findReadOnlyByUsername(String username);

  // JPA Lock
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  List<Member> findLockByUsername(String name);
}
