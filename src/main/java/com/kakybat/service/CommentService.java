package com.kakybat.service;

import com.kakybat.model.Comment;
import com.kakybat.model.Post;
import com.kakybat.model.User;
import com.kakybat.repository.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

//@Service
//public class CommentService {
//    private final CommentRepository commentRepository;
//
//    public CommentService(CommentRepository commentRepository){
//        this.commentRepository = commentRepository;
//    }
//
//    public List<Comment> getCommentByPostId(Long postId){
//        return commentRepository.findByPostId(postId);
//    }
//
//    public Comment createComment(Comment comment){
//        //Implement any business logic/validation here before saving
//        return commentRepository.save(comment);
//    }
//
//    public Comment updateComment(Long commentId, Comment updatedComment){
//        Comment existingComment = commentRepository.findById(commentId)
//                .orElseThrow(() ->new EntityNotFoundException("Comment not found with id: " + commentId));
//        // update existing comment with new data
//        existingComment.setBody(updatedComment.getBody());
//        existingComment.setUpdatedAt(LocalDateTime.now());
//
//        // optionally, you can perform additional validation or business logic here
//        return commentRepository.save(existingComment);
//    }
//
//    public void deleteComment(Long  commentId){
//        Comment existingComment = commentRepository.findById(commentId)
//                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));
//        commentRepository.delete(existingComment);
//    }
//}
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAllCommentsForPost(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public Comment publishComment(Long postId, Comment comment) {
        // Retrieve post by postId and set it to the comment
        Post post = new Post(); // Replace this with your logic to retrieve the post
        comment.setPost(post);

        // Set user for the comment (assuming you have user information available)
        User user = new User(); // Replace this with your logic to retrieve the authenticated user
        comment.setUser(user);

        // Set createdAt timestamp to current time
        comment.setCreatedAt(LocalDateTime.now());

        // Set updatedAt timestamp to current time
        comment.setUpdatedAt(LocalDateTime.now());

        // Save the comment
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}