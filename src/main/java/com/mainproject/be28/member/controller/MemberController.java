package com.mainproject.be28.member.controller;

import com.mainproject.be28.auth.userdetails.UserDetailService;
import com.mainproject.be28.board.dto.BoardDto;
import com.mainproject.be28.board.entity.Board;
import com.mainproject.be28.board.repository.BoardRepository;
import com.mainproject.be28.comment.dto.CommentDto;
import com.mainproject.be28.comment.entity.Comment;
import com.mainproject.be28.comment.repository.CommentRepository;
import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.member.dto.MemberPatchDto;
import com.mainproject.be28.member.dto.MemberPostDto;
import com.mainproject.be28.member.dto.MemberResponseDto;
import com.mainproject.be28.member.dto.PasswordPatchDto;
import com.mainproject.be28.member.mapper.MemberMapper;
import com.mainproject.be28.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.mainproject.be28.member.entity.Member;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper mapper;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    //회원가입
    @PostMapping("/new")
    public ResponseEntity<MemberResponseDto> postMember(@Valid @RequestBody MemberPostDto memberPostDto) {
        Member member = mapper.memberPostToMember(memberPostDto);
        Member savedMember = memberService.createMember(member);
        MemberResponseDto responseDto = mapper.memberToMemberResponse(savedMember);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }


    // 회원 정보 수정
    @PatchMapping("/myPage/{memberId}")
    public ResponseEntity<MemberResponseDto> patchMember(@PathVariable("memberId") Long memberId,
                                                         @RequestBody MemberPatchDto requestBody) {
        requestBody.setMemberId(memberId);
        Member updatedMember = memberService.updateMember(memberId, requestBody);

        if (updatedMember != null) {
            MemberResponseDto responseDto = mapper.memberToMemberResponse(updatedMember);
            return ResponseEntity.ok(responseDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 회원 정보 조회
    @GetMapping("/myPage/{memberId}")
    public ResponseEntity<MemberResponseDto> getMember(@PathVariable("memberId") Long memberId) {
        Member response = memberService.findMember(memberId);

        return new ResponseEntity<>(mapper.memberToMemberResponse(response),HttpStatus.OK);
    }


      //회원탈퇴
    @DeleteMapping("/myPage/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") Long memberId){
        memberService.deleteMember(memberId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/myPage/changePassword/{memberId}")
    public ResponseEntity updatePassword(@AuthenticationPrincipal UserDetailService userDetails,
                                         @RequestBody @Valid PasswordPatchDto passwordPatchDto) {
        log.info("### PW PATCH 시작합니다!");
        String username = userDetails.getUsername(); // 사용자 이름 또는 ID
        String password = passwordPatchDto.getPassword();
        String afterPassword = passwordPatchDto.getAfterPassword();
        log.info("###PW = " + password + ", AFTER PW = " + afterPassword);
        memberService.updatePassword(username, password, afterPassword);

        return ResponseEntity.ok().build();
    }


    //좋아요한 게시물 조회
    @GetMapping("/myPage/likedPost/{likeCount}")
    public ResponseEntity<List<BoardDto>> getLikedPosts(@PathVariable("likeCount") Long likeCount) {
        Optional<Board> user = boardRepository.findById(likeCount);

        List<BoardDto> likedPosts = memberService.getLikedPost(user);
        return ResponseEntity.ok(likedPosts);
    }

    //작성한 게시물 조회
    @GetMapping("/myPage/myPost/{boardId}")
    public ResponseEntity<List<BoardDto>> getMyPosts(@PathVariable("boardId") Long boardId) {
        Optional<Board> user = boardRepository.findById(boardId);

        List<BoardDto> myPosts = memberService.getPostsByUser(user);
        return ResponseEntity.ok(myPosts);
    }

    //작성한 댓글 조회
    @GetMapping("/myPage/myComment/{commentId}")
    public ResponseEntity<List<CommentDto>> getMyComments(@PathVariable("commentId") Long commentId) {
        Optional<Comment> user = commentRepository.findById(commentId);

        List<CommentDto> myComments = memberService.getCommentsByUser(user);
        return ResponseEntity.ok(myComments);
    }
}


