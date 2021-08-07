package study.datajpa.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class MemberController {

  private final MemberRepository memberRepository;

  // 도메인 클래스 컨버터로 엔티티를 파라미터로 받으면, 이 엔티티는 단순 조회용으로만 사용해야 한다.
  // (트랜잭션이 없는 범위에서 엔티티를 조회했으므로, 엔티티를 변경해도 DB에 반영되지 않는다.)
  @GetMapping("/members/{id}")
  public String findMember(@PathVariable("id") Member member) {
    return member.getUsername();
  }


  //예) /members?page=0&size=3&sort=id,desc&sort=username,desc
  // page: 현재 페이지, 0부터 시작한다.
  // Page.map 사용하여 DTO 로 리턴해야함
  // Page 0 부터임, 1부터하려면 새로 클래스 만들어서 리턴해줘야함
  @GetMapping("/members")
  public Page<MemberDto> list(@PageableDefault(size = 10, sort = "username", direction = Sort.Direction.DESC) Pageable pageable) {
    return memberRepository.findAll(pageable).map(MemberDto::new);
  }
}
