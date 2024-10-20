package jpabook2.jpashop2.api;

import jakarta.validation.Valid;
import jpabook2.jpashop2.domain.Member;
import jpabook2.jpashop2.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
     * v1: 요청값으로 Member 엔티티를 직접 받는다.
     * 이렇게 되면 엔티티에 API 검증을 위한 로직이 추가된다
     * -> 엔티티가 변경되면 API 스펙이 변하게 된다.
     *
     * 이런 문제를 일으키지 않기 위해서는 API 요청 스펙에 맞추어 DTO를 파라미터로 받는다.
     * @param member
     * @return
     */
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);

        return new CreateMemberResponse(id);
    }

    /**
     * 요청 값으로 Member 엔티티 대신 별도 DTO를 받는다.
     * @param request
     * @return
     */
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {

        Member member = new Member();
        member.setUsername(request.getName());

        Long id = memberService.join(member);

        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request) {

        memberService.update(id, request.getName());

        Optional<Member> findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.get().getId(), findMember.get().getUsername());

    }

    /**
     * 회원조회 v1: 응답값으로 Member 엔티티를 직접 외부에 노출한다.
     * 문제점
     * - 엔티티에 프레젠테이션 계층을 위한 로직이 추가된다.
     * - 그리고 엔티티의 모든 값이 노출된다.
     * - 응답 스펙을 맞추기 위한 로직이 추가된다.
     * - 엔티티가 변경되면 API 스펙이 변한다.
     * - 컬렉션을 직접 반환하면 향후 API 스펙을 변경하기가 어렵다 -> Result 클래스로 해결
     *
     *  API 응답 스펙에 맞추어 별도 DTO를 반환하도록 하자.
     * @return
     */
    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result memberV2() {

        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getUsername()))
                .collect(Collectors.toList());

        return new Result(collect.size()    ,collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }


    @Data
    static class CreateMemberRequest {
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
