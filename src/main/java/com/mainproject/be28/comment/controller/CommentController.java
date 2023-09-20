package com.mainproject.be28.comment.controller;

import com.mainproject.be28.comment.dto.CommentPatchDto;
import com.mainproject.be28.comment.dto.CommentPostDto;
import com.mainproject.be28.comment.dto.CommentResponseDto;
import com.mainproject.be28.comment.mapper.CommentMapper;
import com.mainproject.be28.comment.service.CommentService;
import com.mainproject.be28.response.SingleResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentMapper mapper;

    @PostMapping("/new")
    public ResponseEntity createComment(@RequestBody CommentPostDto commentPostDto) {
        SingleResponseDto response = new SingleResponseDto(commentService.createComment(commentPostDto), HttpStatus.CREATED);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity getCommentById(@PathVariable("commentId") Long commentId){
        CommentResponseDto comment = commentService.getCommentById(commentId);
        SingleResponseDto response = new SingleResponseDto(comment, HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity updateComment(@PathVariable Long commentId, @RequestBody CommentPatchDto commentPatchDto) {
        CommentResponseDto comment = commentService.updateComment(commentId, commentPatchDto);
        SingleResponseDto response = new SingleResponseDto(comment, HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}