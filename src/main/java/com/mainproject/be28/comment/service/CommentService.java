package com.mainproject.be28.comment.service;

import com.mainproject.be28.comment.entity.Comment;
import com.mainproject.be28.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Comment createComment(Comment comment){
        return commentRepository.save(comment);
    }

    public Comment getCommentById(Long id){
        return commentRepository.findById(id).orElse(null);
    }

    public Comment updateComment(Long id, Comment updatedComment) {
        Comment existingComment = commentRepository.findById(id).orElse(null);
        if (existingComment != null) {
            if (updatedComment.getContent() != null) {
                existingComment.setContent(updatedComment.getContent());
            }
            return commentRepository.save(existingComment);
        }
        return null;
    }
    public void deleteComment(Long id){
        commentRepository.deleteById(id);
    }
}


