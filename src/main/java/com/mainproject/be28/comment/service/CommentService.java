package com.mainproject.be28.comment.service;

import com.mainproject.be28.comment.dto.CommentPatchDto;
import com.mainproject.be28.comment.dto.CommentPostDto;
import com.mainproject.be28.comment.dto.CommentResponseDto;
import com.mainproject.be28.comment.entity.Comment;
import com.mainproject.be28.comment.mapper.CommentMapper;
import com.mainproject.be28.comment.repository.CommentRepository;
import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.exception.ExceptionCode;
import com.mainproject.be28.member.entity.Member;
import com.mainproject.be28.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentMapper mapper;
    @Autowired
    private MemberService memberService;

    public CommentResponseDto createComment(CommentPostDto commentPostDto){
        Comment comment = mapper.commentPostDtoToComment(commentPostDto);
        Member member = memberService.findTokenMember();
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
        Member member = memberService.findTokenMember();
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
        verifySameMember(memberService.findTokenMember(),comment);

        commentRepository.delete(comment);
    }
}


