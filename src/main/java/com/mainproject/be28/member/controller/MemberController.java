package com.mainproject.be28.member.controller;

import com.mainproject.be28.comment.dto.CommentResponseDto;
import com.mainproject.be28.complain.dto.ComplainResponseDto;
import com.mainproject.be28.member.dto.MemberPatchDto;
import com.mainproject.be28.member.dto.MemberPostDto;
import com.mainproject.be28.member.dto.PasswordPatchDto;
import com.mainproject.be28.member.mapper.MemberMapper;
import com.mainproject.be28.member.service.MemberService;
import com.mainproject.be28.member.service.MyPageService;
import com.mainproject.be28.response.MultiResponseDto;
import com.mainproject.be28.response.SingleResponseDto;
import com.mainproject.be28.review.dto.ReviewResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mainproject.be28.member.entity.Member;

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
        Member updatedMember = mypageService.updateProfile(requestBody);

        if (updatedMember != null) {
            SingleResponseDto response = new SingleResponseDto(mapper.memberToMemberResponse(updatedMember), HttpStatus.OK);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //회원 비밀번호 수정
    @PatchMapping("/myPage/pw")
    public ResponseEntity patchPassword(@RequestBody PasswordPatchDto requestBody) {
        Member member = mypageService.changePassword(requestBody);
        return ResponseEntity.ok().build();
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
        mypageService.deleteMember(email, password);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //작성한 게시물 조회
    @GetMapping("/myPage/myReview")
    public ResponseEntity<List<ReviewResponseDto>> getMyReviews(@RequestParam int page,@RequestParam int size) {
        long memberId = memberService.findTokenMemberId();
        Page<ReviewResponseDto> myReviews = mypageService.getMyReviews(memberId,page,size);
        MultiResponseDto response = new MultiResponseDto<>(myReviews.getContent(), myReviews, HttpStatus.OK);
        return new ResponseEntity(response,HttpStatus.OK);
    }

    //작성한 댓글 조회
    @GetMapping("/myPage/myComment")
    public ResponseEntity<List<CommentResponseDto>> getMyComments(@RequestParam int page,@RequestParam int size) {
        
        Page<CommentResponseDto> myComments = mypageService.getMyComments(page, size);
        MultiResponseDto response = new MultiResponseDto<>(myComments.getContent(), myComments, HttpStatus.OK);
        return new ResponseEntity(response,HttpStatus.OK);
    }
    //작성한  문의 조회
    @GetMapping("/myPage/myComplain")
    public ResponseEntity<List<ComplainResponseDto>> getMyComplains(@RequestParam int page,@RequestParam int size) {
        
        Page<ComplainResponseDto> myComplains = mypageService.getMyComplains(page, size);
        MultiResponseDto response = new MultiResponseDto<>(myComplains.getContent(), myComplains, HttpStatus.OK);
        return new ResponseEntity(response,HttpStatus.OK);
    }
}