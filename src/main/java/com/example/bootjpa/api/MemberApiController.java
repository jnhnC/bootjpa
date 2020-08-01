package com.example.bootjpa.api;

import com.example.bootjpa.domain.Address;
import com.example.bootjpa.domain.Member;
import com.example.bootjpa.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    //등록
    //엔티티를 직접받는 경우
    //엔티티를 바로 받으면 Member 스펙이 변경 될 경우 api를 사용하는 곳에 에러가 발생할 수 있으므로 사용 X
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }
    //등록
    //DTO로 받고 Member생성해서 넣어주는 경우 스펙이 바뀌어도 api 자체에서만 바꾸면 다른 곳에 영양을 받지 않는다.
    //넘기거나 받거나 할때 항상 DTO를 생성하여 사용하는게 최적!
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member();
        member.setName(request.getName());
        member.setAddress(new Address(request.city, request.street, request.zipcode));

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    //수정
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request){

        memberService.update(id, request.getName()); //업데이트 끝
        Member findMember = memberService.findOne(id); //끝난후 업데이트 내용 확인하기
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }


    //기본 엔티티 조회
    //엔티티 받을 시에도 마찬가지로 DTO를 사용한다
    @GetMapping("/api/v2/members")
    public Result member(){
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(member -> new MemberDto(member.getName()))
                .collect(toList());

        return new Result(collect);
    }

    //Member로 Order 내용 함께 조회
    //엔티티 받을 시에도 마찬가지로 DTO를 사용한다
    @GetMapping("/api/v2/members")
    public Result memberWithOrder(){
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(member -> new MemberDto(member.getName()))
                .collect(toList());

        return new Result(collect);
    }


    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }


    //받아온 request에 대한 DTO
    @Data
    private static class CreateMemberRequest {
        @NotEmpty
        private String name;

        private String city;
        private String street;
        private String zipcode;
    }
    //넘겨줄 response에 대한 DTO
    @Data
    static class CreateMemberResponse {
        private Long id;
        public CreateMemberResponse(Long id){
            this.id=id;
        }
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

    @Data
    @AllArgsConstructor
    private class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    private class UpdateMemberRequest {
        private String name;

    }
}
