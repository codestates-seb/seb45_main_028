package com.mainproject.be28.member.controller;

import com.mainproject.be28.member.dto.MemberPatchDto;
import com.mainproject.be28.member.dto.MemberPostDto;
import com.mainproject.be28.member.dto.MemberResponseDto;
import com.mainproject.be28.member.mapper.MemberMapper;
import com.mainproject.be28.member.repository.MemberRepository;
import com.mainproject.be28.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mainproject.be28.member.entity.Member;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper mapper;

    @PostMapping("/new")
    public ResponseEntity<MemberResponseDto> postMember(@Valid @RequestBody MemberPostDto memberPostDto) {
        Member member = mapper.memberPostToMember(memberPostDto);
        Member savedMember = memberService.createMember(member);
        MemberResponseDto responseDto = mapper.memberToMemberResponse(savedMember);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }


    //회원정보수정
    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") Long memberId,
                                      @RequestBody MemberPatchDto requestBody){
        requestBody.setMemberId(memberId);

        Member response = memberService.updateMember(mapper.memberPatchToMember(requestBody),memberId);

        return new ResponseEntity<>(mapper.memberToMemberResponse(response),HttpStatus.OK);
    }


    //회원정보조회
    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") Long memberId){
        Member response = memberService.findMember(memberId);

        return new ResponseEntity<>(mapper.memberToMemberResponse(response),HttpStatus.OK);
    }


//    @GetMapping
//    public ResponseEntity getMembers(){
//        List<Member> members = memberService.findMembers();
//
//        List<MemberRepository> response = members.stream()
//                .map(member -> {
//                    MemberResponseDto dto = mapper.memberToMemberResponse(member);
//                })
//                .collect(Collectors.toList());
//        return new ResponseEntity(response, HttpStatus.OK);
//    }

      //회원탈퇴
    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteAnswer(@PathVariable("member-id") Long memberId){
        memberService.deleteMember(memberId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}


