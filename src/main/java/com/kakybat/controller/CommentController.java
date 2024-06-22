package com.kakybat.controller;

import com.kakybat.model.Comment;
import com.kakybat.model.Person;
import com.kakybat.model.Post;
import com.kakybat.repository.CommentRepository;
import com.kakybat.service.PersonService;
import com.kakybat.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/comments")
public class CommentController {
//    private final CommentService commentService;
    private final PersonService personService;
    private final PostService postService;
    private final CommentRepository commentRepository;

    public CommentController(PersonService personService, PostService postService, CommentRepository commentRepository){
//        this.commentService = commentService;
        this.personService = personService;
        this.postService = postService;
        this.commentRepository = commentRepository;
    }

    // Endpoint to retrieve all comments for a post
//    @GetMapping("/post/{postId}")
//    public String getCommentsByPostId(@PathVariable Long postId, Model model){
//        List<Comment> comments = commentService.getCommentByPostId(postId);
//        model.addAttribute("comments", comments);
//        return "post";
//    }


    // Endpoint to create a new Comment
    @PostMapping("/posts/{postId}")
    public String createComment(@ModelAttribute Comment comment, Principal principal, @PathVariable Long postId){
        String authUsername;

        if(principal != null){
            authUsername = principal.getName();
        } else {
            throw new IllegalArgumentException("Account not found");
        }
        Person person = personService.findUserByEmail(authUsername);
        comment.setPerson(person);

        Optional<Post> optionalPost = postService.getById(postId);
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            comment.setPost(post);
            commentRepository.save(comment);
            return "redirect:/posts/" + post.getId();
        } else {
            throw new IllegalArgumentException("Post not found");
        }
    }

    // Endpoint to update existing comment
//    @PutMapping("/{commentId}/edit")
//    public String updateComment(@PathVariable Long commentId, @ModelAttribute Comment updateComment){
//        commentService.updateComment(commentId, updateComment);
//        return "redirect:/comments/" + updateComment.getPost().getId();
//    }

    // Endpoint to delete an existing comment
    @DeleteMapping("/{commentId}/delete")
//    public String deleteComment(@PathVariable Long commentId, @ModelAttribute Comment comment){
    public void deleteComment(@PathVariable Long commentId, @ModelAttribute Comment comment){
//        commentService.deleteComment(commentId);
        commentRepository.deleteById(commentId);
//        return "redirect:/comments/" + comment.getPost().getId();
    }

}
//@RestController
//@RequestMapping("/api/comments")
//public class CommentController {
//    private final CommentService commentService;
//
//    public CommentController(CommentService commentService) {
//        this.commentService = commentService;
//    }
//
//    @GetMapping("/posts/{postId}")
//    public ResponseEntity<List<Comment>> getAllCommentsForPost(@PathVariable Long postId) {
//        List<Comment> comments = commentService.getAllCommentsForPost(postId);
//        return ResponseEntity.ok(comments);
//    }
//
//    @PostMapping("/posts/{postId}")
//    public ResponseEntity<Comment> publishComment(@PathVariable Long postId, @RequestBody Comment comment) {
//        Comment savedComment = commentService.publishComment(postId, comment);
//        return ResponseEntity.ok(savedComment);
//    }
//
//    @DeleteMapping("/{commentId}")
//    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
//        commentService.deleteComment(commentId);
//        return ResponseEntity.noContent().build();
//    }
//}
