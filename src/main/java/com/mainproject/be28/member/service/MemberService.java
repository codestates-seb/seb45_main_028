package com.mainproject.be28.member.service;

import com.mainproject.be28.auth.userdetails.MemberAuthority;
import com.mainproject.be28.board.dto.BoardDto;
import com.mainproject.be28.board.entity.Board;
import com.mainproject.be28.comment.dto.CommentDto;
import com.mainproject.be28.comment.entity.Comment;
import com.mainproject.be28.comment.repository.CommentRepository;
import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.exception.ExceptionCode;
import com.mainproject.be28.member.dto.MemberPatchDto;
import com.mainproject.be28.member.entity.Member;
import com.mainproject.be28.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberAuthority memberAuthority;



    //회원생성
    public Member createMember(Member member){
        String password = "{noop}" + member.getPassword();
        member.setPassword(password);
        // 패스워드 암호화
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        // 권한 추가
        List<String> roles = memberAuthority.createRoles(member.getEmail());
        member.setRoles(roles);

        return memberRepository.save(member);
    }


    public Member updateMember(Long memberId, MemberPatchDto requestBody) {
        Member findMember = findVerifiedMember(memberId);

        Optional.ofNullable(requestBody.getPhone())
                .ifPresent(phone -> findMember.setPhone(phone));
        Optional.ofNullable(requestBody.getAddress())
                .ifPresent(address -> findMember.setAddress(address));

        Member savedMember = memberRepository.save(findMember);
        return savedMember;
    }

    public Member findMember(Long memberId) {
        return findVerifiedMember(memberId);
    }

    //회원 탈퇴
    public void deleteMember(Long memberId){
        Member member = findVerifiedMember(memberId);

        memberRepository.delete(member);
    }

    public Member findVerifiedMember(Long memberId){
        Optional<Member> optionalMember =
                memberRepository.findById(memberId);
        Member findMember =
                optionalMember.orElseThrow();
        return findMember;
    }


    //비밀번호 변경
    public Member updatePassword(String email, String password, String afterPassword) {
        // 회원이 존재하는지 검증
        log.info("### 회원이 존재하는지 검증합니다!");
        Optional<Member> findUserOpt = checkUserExist(email);
        log.info("### 검증 완료!");

        // 회원이 존재하지 않을 경우 예외 처리
        Member findUser = findUserOpt.orElseThrow();

        // 비밀번호가 일치하는지 검증
        log.info("### 비밀번호가 일치하는지 검증합니다!");
        if (passwordEncoder.matches(password, findUser.getPassword())) {
            // 비밀번호 변경
            findUser.setPassword(passwordEncoder.encode(afterPassword));
            memberRepository.save(findUser);
        } else {
            throw new BusinessLogicException(ExceptionCode.INCORRECT_PASSWORD);
        }

        return findUser;
    }

    private Optional<Member> checkUserExist(String email) {
        // 이메일을 사용하여 회원을 찾음
        Optional<Member> user = memberRepository.findByEmail(email);

        // 회원이 존재하지 않을 경우 Optional.empty() 반환
        return user;
    }


    //좋아요한 게시물
    public List<BoardDto> getLikedPost(Optional<Board> likeBoard) {
        if (likeBoard.isPresent()) {
            Board board = likeBoard.get();
            BoardDto boardDto = convertToBoardDto(board);
            return Collections.singletonList(boardDto);
        } else {
            return Collections.emptyList();
        }
    }
    private BoardDto convertToBoardDto(Board board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setBoardId(board.getBoardId());
        boardDto.setLikeCount(board.getLikeCount());
        return boardDto;
    }


//    //작성한 게시물 조회
//    public List<BoardDto> getPostsByUser(Optional<Board> board) {
//        if (board.isPresent()) {
//            Board userBoard = board.get();
//            BoardDto boardDto = convertToBoardDto(userBoard);
//            return Collections.singletonList(boardDto);
//        } else {
//            return Collections.emptyList();
//        }
//    }

    //작성한 댓글 조회
    public List<CommentDto> getCommentsByUser(Optional<Comment> comment) {
        if (comment.isPresent()) {
            Comment userComment = comment.get();

            List<CommentDto> userComments = convertToCommentDto(userComment);
            return userComments;
        } else {
            return Collections.emptyList();
        }
    }
    private List<CommentDto> convertToCommentDto(Comment comment) {
        List<CommentDto> commentDtos = new ArrayList<>();

        CommentDto commentDto = new CommentDto();
        commentDto.setCommentId(comment.getCommentId());
        commentDto.setContent(comment.getContent());
        // 필요한 다른 속성들을 설정
        commentDtos.add(commentDto);
        return commentDtos;
    }

    // 토큰으로 멤버객체 찾기
    public Optional<Member> findMemberFromToken() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Member> optionalMember = memberRepository.findByEmail(username);

        return optionalMember;
    }


}
