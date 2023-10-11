package com.mainproject.be28.domain.community.comment.service;

import com.mainproject.be28.domain.community.comment.dto.CommentPatchDto;
import com.mainproject.be28.domain.community.comment.dto.CommentPostDto;
import com.mainproject.be28.domain.community.comment.dto.CommentResponseDto;
import com.mainproject.be28.domain.community.comment.entity.Comment;
import com.mainproject.be28.domain.community.comment.mapper.CommentMapper;
import com.mainproject.be28.domain.community.comment.repository.CommentRepository;
import com.mainproject.be28.domain.member.service.Layer2.MemberVerifyService;
import com.mainproject.be28.global.exception.BusinessLogicException;
import com.mainproject.be28.global.exception.ExceptionCode;
import com.mainproject.be28.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper mapper;
    private final MemberVerifyService memberVerifyService;

    public CommentService(CommentRepository commentRepository, CommentMapper mapper, MemberVerifyService memberVerifyService) {
        this.commentRepository = commentRepository;
        this.mapper = mapper;
        this.memberVerifyService = memberVerifyService;
    }

    public CommentResponseDto createComment(CommentPostDto commentPostDto){
        Comment comment = mapper.commentPostDtoToComment(commentPostDto);
        Member member = memberVerifyService.findTokenMember();
        comment.setMember(member);
        comment.setLikeCount(0L);
        commentRepository.save(comment);
        return  mapper.commentToCommentResponseDto(comment);
    }

    public CommentResponseDto getCommentById(Long id){
        Comment comment  = commentRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        return mapper.commentToCommentResponseDto(comment);
    }

    public CommentResponseDto updateComment(Long id, CommentPatchDto updatedComment) {
        Member member = memberVerifyService.findTokenMember();
        Comment originComment = commentRepository.findById(updatedComment.getCommentId()).orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        Comment comment = mapper.commentPatchDtoToComment(updatedComment);

        verifySameMember(member, originComment);
        updateContent(originComment, comment);

        return mapper.commentToCommentResponseDto(originComment);
        }

    private void updateContent(Comment originComment, Comment comment) {
        if (originComment != null&& comment.getContent() != null) {
            originComment.setContent(comment.getContent());
            commentRepository.save(originComment);
        }
    }

    private static void verifySameMember(Member member, Comment comment) {
        if (!comment.getMember().equals(member)) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_AUTHORIZED);
        }
    }

    public void deleteComment(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        verifySameMember(memberVerifyService.findTokenMember(),comment);

        commentRepository.delete(comment);
    }

    public Page<CommentResponseDto> findCommentsByMember(String name,int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        List<Comment> myComments = commentRepository.findCommentsByMember_Name(name);
        List<CommentResponseDto> myCommentsDto = new ArrayList<>();
        for (Comment comment : myComments) {
            myCommentsDto.add(mapper.commentToCommentResponseDto(comment));
        }
        return new PageImpl<>(myCommentsDto, pageRequest, myCommentsDto.size());
    }
}


