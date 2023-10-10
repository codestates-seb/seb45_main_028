package com.mainproject.be28.domain.member.controller;

import com.mainproject.be28.domain.community.comment.dto.CommentResponseDto;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainResponseDto;
import com.mainproject.be28.domain.member.dto.MemberPatchDto;
import com.mainproject.be28.domain.member.dto.MemberPostDto;
import com.mainproject.be28.domain.member.dto.PasswordPatchDto;
import com.mainproject.be28.domain.member.entity.Member;
import com.mainproject.be28.domain.member.mapper.MemberMapper;
import com.mainproject.be28.domain.member.service.MemberService;
import com.mainproject.be28.domain.member.service.MyPageService;
import com.mainproject.be28.global.response.MultiResponseDto;
import com.mainproject.be28.global.response.SingleResponseDto;
import com.mainproject.be28.domain.shopping.review.dto.ReviewResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper mapper;
    private final MyPageService mypageService;

    //회원가입
    @PostMapping("/new")
    public ResponseEntity postMember(@Valid @RequestBody MemberPostDto memberPostDto) {
        Member member = mapper.memberPostToMember(memberPostDto);
        Member savedMember = memberService.createMember(member);
        SingleResponseDto responseDto = new SingleResponseDto<>(mapper.memberToMemberResponse(savedMember), HttpStatus.CREATED);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }


    // 회원 개인정보(주소,전화번호) 수정
    @PatchMapping("/myPage")
    public ResponseEntity patchProfile(@RequestBody MemberPatchDto requestBody) {
        Member updatedMember = memberService.updateProfile(requestBody);

        SingleResponseDto response = new SingleResponseDto(mapper.memberToMemberResponse(updatedMember), HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

    //회원 비밀번호 수정
    @PatchMapping("/myPage/pw")
    public ResponseEntity patchPassword(@RequestBody PasswordPatchDto requestBody) {
        memberService.changePassword(requestBody);
        SingleResponseDto response = new SingleResponseDto<>("비밀번호 변경 성공", HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

    // 회원 정보 조회
    @GetMapping("/myPage")
    public ResponseEntity getMember() {
        Member response = memberService.findTokenMember();
        SingleResponseDto responseDto = new SingleResponseDto(mapper.memberToMemberResponse(response),HttpStatus.OK);
        return ResponseEntity.ok(responseDto);
    }

    //회원탈퇴
    @DeleteMapping("/myPage")
    public ResponseEntity deleteMember(@RequestBody String email,@RequestBody String password){
        memberService.deleteMember(email, password);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //작성한 게시물 조회
    @GetMapping("/myReview")
    public ResponseEntity<List<ReviewResponseDto>> getMyReviews(@RequestParam int page,@RequestParam int size) {
        long memberId = memberService.findTokenMemberId();
        Page<ReviewResponseDto> myReviews = mypageService.getMyReviews(memberId,page,size);
        MultiResponseDto response = new MultiResponseDto<>(myReviews.getContent(), myReviews, HttpStatus.OK);
        return new ResponseEntity(response,HttpStatus.OK);
    }

    //작성한 댓글 조회
    @GetMapping("/myComment")
    public ResponseEntity<List<CommentResponseDto>> getMyComments(@RequestParam int page,@RequestParam int size) {
        
        Page<CommentResponseDto> myComments = mypageService.getMyComments(page, size);
        MultiResponseDto response = new MultiResponseDto<>(myComments.getContent(), myComments, HttpStatus.OK);
        return new ResponseEntity(response,HttpStatus.OK);
    }
    //작성한  문의 조회
    @GetMapping("/myComplain")
    public ResponseEntity<List<ComplainResponseDto>> getMyComplains(@RequestParam int page, @RequestParam int size) {
        
        Page<ComplainResponseDto> myComplains = mypageService.getMyComplains(page, size);
        MultiResponseDto response = new MultiResponseDto<>(myComplains.getContent(), myComplains, HttpStatus.OK);
        return new ResponseEntity(response,HttpStatus.OK);
    }
}