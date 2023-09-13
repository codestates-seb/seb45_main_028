package com.mainproject.be28.member.service;

import com.mainproject.be28.board.dto.BoardDto;
import com.mainproject.be28.board.entity.Board;
import com.mainproject.be28.board.repository.BoardRepository;
import com.mainproject.be28.comment.dto.CommentDto;
import com.mainproject.be28.comment.entity.Comment;
import com.mainproject.be28.comment.repository.CommentRepository;
import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.exception.ExceptionCode;
import com.mainproject.be28.member.entity.Member;
import com.mainproject.be28.member.repository.MemberRepository;
import com.mainproject.be28.review.dto.ReviewResponseDto;
import com.mainproject.be28.review.entity.Review;
import com.mainproject.be28.review.mapper.ReviewMapper;
import com.mainproject.be28.review.repository.ReviewRepository;
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


    //회원생성
    public Member createMember(Member member){
        String password = "{noop}" + member.getPassword();
        member.setPassword(password);
        return memberRepository.save(member);
    }

    public Member findMember(Long memberId) {
        return findVerifiedMember(memberId);
    }

    public Member findVerifiedMember(Long memberId){
        Optional<Member> optionalMember =
                memberRepository.findById(memberId);
        Member findMember =
                optionalMember.orElseThrow();
        return findMember;
    }

//    //비밀번호 변경
//    public Member updatePassword(String email, String password, String afterPassword) {
//        // 회원이 존재하는지 검증
//        log.info("### 회원이 존재하는지 검증합니다!");
//        Optional<Member> findUserOpt = checkUserExist(email);
//        log.info("### 검증 완료!");
//
//        // 회원이 존재하지 않을 경우 예외 처리
//        Member findUser = findUserOpt.orElseThrow();
//
//        // 비밀번호가 일치하는지 검증
//        log.info("### 비밀번호가 일치하는지 검증합니다!");
//        if (passwordEncoder.matches(password, findUser.getPassword())) {
//            // 비밀번호 변경
//            findUser.setPassword(passwordEncoder.encode(afterPassword));
//            memberRepository.save(findUser);
//        } else {
//            throw new BusinessLogicException(ExceptionCode.INCORRECT_PASSWORD);
//        }
//
//        return findUser;
//    }

    private Optional<Member> checkUserExist(String email) {
        // 이메일을 사용하여 회원을 찾음
        Optional<Member> user = memberRepository.findByEmail(email);

        // 회원이 존재하지 않을 경우 Optional.empty() 반환
        return user;
    }


    //좋아요한 게시물
//    public List<BoardDto> getLikedPost(Optional<Board> likeBoard) {
//        if (likeBoard.isPresent()) {
//            Board board = likeBoard.get();
//            BoardDto boardDto = convertToBoardDto(board);
//            return Collections.singletonList(boardDto);
//        } else {
//            return Collections.emptyList();
//        }
//    }






    public Long findTokenMemberId() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return memberRepository.findMemberByEmail(email).getMemberId();
    }
    public Member findTokenMember() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return memberRepository.findMemberByEmail(email);
    }

    //현재 로그인한 회원정보 접근 시 회원 이메일/비밀번호 한번더 검증
    public void verifyEmailPassword(String email, String password) {
        String currentEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 현재 로그인되어 있는 회원의 메일
        Member member = memberRepository.findMemberByEmail(currentEmail); //현재 회원정보

        String currentPassword = member.getPassword(); // 현재 로그인 되어있는 회원의 비밀번호

        boolean matchEmail = email.equals(currentEmail); //실제 이메일과 입력한 이메일이 일치하는지
        boolean matchPassword = password.equals(currentPassword); // 실제 비밀번호와 입력한 비밀번확 일치하는지
        if(!matchEmail||!matchPassword){
            throw new BusinessLogicException(ExceptionCode.VERIFY_FAILURE); // 둘 중하나라도 다르다면 인증 실
        }
    }
}
