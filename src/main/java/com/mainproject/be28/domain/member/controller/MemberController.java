package com.mainproject.be28.domain.member.controller;

import com.mainproject.be28.domain.community.comment.dto.CommentResponseDto;
import com.mainproject.be28.domain.member.dto.MemberResponseDto;
import com.mainproject.be28.domain.member.dto.MemberPatchDto;
import com.mainproject.be28.domain.member.dto.MemberPostDto;
import com.mainproject.be28.domain.member.dto.PasswordPatchDto;
import com.mainproject.be28.domain.member.service.Layer1.MemberService;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainResponsesDto;
import com.mainproject.be28.global.response.MultiResponseDto;
import com.mainproject.be28.global.response.SingleResponseDto;
import com.mainproject.be28.domain.shopping.review.dto.ReviewResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    //회원가입
    @PostMapping("/new")
    @ApiOperation(value = "회원 가입 API", notes = "이메일을 아이디로 사용하여 가입")
    public ResponseEntity<SingleResponseDto<MemberResponseDto>> postMember(@Valid @RequestBody MemberPostDto memberPostDto) {
        MemberResponseDto savedMember = memberService.createMember(memberPostDto);
        SingleResponseDto<MemberResponseDto> responseDto = new SingleResponseDto<>(savedMember, HttpStatus.CREATED);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    //회원탈퇴
    @DeleteMapping("/myPage")
    @ApiOperation(value = "회원 탈퇴 API", notes = "로그인 검증을 통해 회원 탈퇴")
    public ResponseEntity<HttpStatus> deleteMember(@RequestBody String email,@RequestBody String password){
        memberService.deleteMember(email, password);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 회원 개인정보(주소,전화번호) 수정
    @PatchMapping("/profile")
    public ResponseEntity<SingleResponseDto<MemberResponseDto>> patchProfile(@RequestBody MemberPatchDto requestBody) {
        MemberResponseDto updatedMember = memberService.updateProfile(requestBody);

        SingleResponseDto<MemberResponseDto> response = new SingleResponseDto<>(updatedMember, HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

    //회원 비밀번호 수정
    @PatchMapping("/password")
    public ResponseEntity<SingleResponseDto<String>> patchPassword(@RequestBody PasswordPatchDto requestBody) {
        memberService.changePassword(requestBody);
        SingleResponseDto<String>response = new SingleResponseDto<>("비밀번호 변경 성공", HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

    // 회원 정보 조회
    @GetMapping("/myPage")
    public ResponseEntity<SingleResponseDto<MemberResponseDto>> getMember() {
        MemberResponseDto response = memberService.getProfile();
        SingleResponseDto<MemberResponseDto> responseDto = new SingleResponseDto<>(response,HttpStatus.OK);
        return ResponseEntity.ok(responseDto);
    }

    //작성한 게시물 조회
    @GetMapping("/myReview")
    public ResponseEntity<MultiResponseDto<ReviewResponseDto>> getMyReviews(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int size) {

        Page<ReviewResponseDto> myReviews =memberService.getMine(page,size, ReviewResponseDto.class);
        MultiResponseDto<ReviewResponseDto> response = new MultiResponseDto<>(myReviews, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //작성한 댓글 조회
    @GetMapping("/myComment")
    public ResponseEntity<MultiResponseDto<CommentResponseDto>> getMyComments(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        
        Page<CommentResponseDto> myComments = memberService.getMine(page, size, CommentResponseDto.class);
        MultiResponseDto<CommentResponseDto> response = new MultiResponseDto<>(myComments, HttpStatus.OK);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    //작성한  문의 조회
    @GetMapping("/myComplain")
    public ResponseEntity<MultiResponseDto<ComplainResponsesDto>> getMyComplains(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        
        Page<ComplainResponsesDto> myComplains = memberService.getMine(page, size, ComplainResponsesDto.class);
        MultiResponseDto<ComplainResponsesDto> response = new MultiResponseDto<>(myComplains, HttpStatus.OK);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}