//package com.kakybat.controller;
//
//import com.kakybat.model.Comment;
//import com.kakybat.model.Post;
//import com.kakybat.model.User;
//import com.kakybat.service.CommentService;
//import com.kakybat.service.UserService;
//import com.kakybat.serviceImpl.PostService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.security.Principal;
//import java.util.List;
//import java.util.Optional;
////
////@Controller
////@RequestMapping("/comments")
////public class CommentController {
////    private final CommentService commentService;
////    private final UserService userService;
////    private final PostService postService;
////    public CommentController(CommentService commentService, UserService userService, PostService postService){
////        this.commentService = commentService;
////        this.userService = userService;
////        this.postService = postService;
////    }
////
////    // Endpoint to retrieve all comments for a post
////    @GetMapping("/post/{postId}")
////    public String getCommentsByPostId(@PathVariable Long postId, Model model){
////        List<Comment> comments = commentService.getCommentByPostId(postId);
////        model.addAttribute("comments", comments);
////        return "post";
////    }
////
////
////    // Endpoint to create a new Comment
////    @PostMapping("/posts/{postId}")
////    public String createComment(@ModelAttribute Comment comment, Principal principal, @PathVariable Long postId){
////        String authUsername;
////
////        if(principal != null){
////            authUsername = principal.getName();
////        } else {
////            throw new IllegalArgumentException("Account not found");
////        }
////        User user = userService.findUserByEmail(authUsername);
////        comment.setUser(user);
////
////        Optional<Post> optionalPost = postService.getById(postId);
////        if(optionalPost.isPresent()){
////            Post post = optionalPost.get();
////            comment.setPost(post);
////            commentService.createComment(comment);
////            return "redirect:/posts/" + post.getId();
////        } else {
////            throw new IllegalArgumentException("Post not found");
////        }
////    }
////
////    // Endpoint to update existing comment
////    @PutMapping("/{commentId}/edit")
////    public String updateComment(@PathVariable Long commentId, @ModelAttribute Comment updateComment){
////        commentService.updateComment(commentId, updateComment);
////        return "redirect:/comments/" + updateComment.getPost().getId();
////    }
////
////    // Endpoint to delete an existing comment
////    @DeleteMapping("/{commentId}/delete")
////    public String deleteComment(@PathVariable Long commentId, @ModelAttribute Comment comment){
////        commentService.deleteComment(commentId);
////        return "redirect:/comments/" + comment.getPost().getId();
////    }
////
////}
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
