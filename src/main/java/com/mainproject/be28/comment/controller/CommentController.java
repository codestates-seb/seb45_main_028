package com.mainproject.be28.comment.controller;

import com.mainproject.be28.comment.dto.CommentDto;
import com.mainproject.be28.comment.entity.Comment;
import com.mainproject.be28.comment.mapper.CommentMapper;
import com.mainproject.be28.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentMapper mapper;

    @PostMapping  // Removed @public
    public Comment createComment(@RequestBody CommentDto commentDto){
        Comment comment = mapper.commentDtoToComment(commentDto);
        return commentService.createComment(comment);
    }

    @GetMapping("/{commentId}")
    public Optional<Comment> getCommentById(@PathVariable("commentId") Long commentId){
        return Optional.ofNullable(commentService.getCommentById(commentId));
    }

    @PatchMapping("/{commentId}")
    public Comment updateComment(@PathVariable Long commentId, @RequestBody CommentDto commentDto) {
        Comment comment = mapper.commentDtoToComment(commentDto);
        return commentService.updateComment(commentId, comment);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}