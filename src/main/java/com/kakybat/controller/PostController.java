package com.kakybat.controller;

import com.kakybat.model.Comment;
import com.kakybat.model.User;
import com.kakybat.model.Post;
import com.kakybat.service.CommentService;
import com.kakybat.service.FileService;
import com.kakybat.serviceImpl.UserServiceImpl;
import com.kakybat.serviceImpl.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class PostController {
    public static Logger logger = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;
    private final UserServiceImpl userService;
    private final FileService fileService;
    private final CommentService commentService;

    public PostController(PostService postService, UserServiceImpl userService, FileService fileService, CommentService commentService){
        this.postService = postService;
        this.userService = userService;
        this.fileService = fileService;
        this.commentService = commentService;
    }

    @GetMapping("/blog")
    public String getPostList(Model model){
        List<Post> posts = postService.getAll();
        model.addAttribute("pageTitle", "Blog");
        model.addAttribute("posts", posts);
        return "blog";
    }

    @GetMapping("/posts/{postId}")
    public String getSinglePost(@PathVariable Long postId, Model model){
        Optional<Post> optionalPost = postService.getById(postId);
        List<Comment> comments = commentService.getAllCommentsForPost(postId);
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            model.addAttribute("comments", comments);
            return "post";
        } else {
            return "p404";
        }
    }
    @GetMapping("/posts/new")
    public String createNewPost(Model model){
            Post post = new Post();
            model.addAttribute("post", post);
            return "new_post";
    }
    @PostMapping("/posts/save")
    public String saveNewPost(@ModelAttribute Post post, Principal principal, MultipartFile file){
        String authUsername = "anonymousUser";
        if(principal != null){
            authUsername = principal.getName();
        } else {
            throw new IllegalArgumentException("Account not found");
        }
        User user = userService.findUserByEmail(authUsername);
        try{
            fileService.save(file);
            post.setImageFilePath(file.getOriginalFilename());
        } catch (Exception e){
            logger.error("Error processing file: {}", file.getOriginalFilename());
        }

        post.setUser(user);
        postService.save(post);
        return "redirect:/posts/" + post.getId();
    }
    @RequestMapping("/posts/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String getPostForEdit(@PathVariable Long id, Model model){
        Optional<Post> optionalPost = postService.getById(id);
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            return "post_edit";
        } else {
            return "p404";
        }
    }

    @PostMapping("/posts/{id}")
    @PreAuthorize("isAuthenticated()")
    public String updatePost(@PathVariable Long id, Post post, @RequestParam("file") MultipartFile file){
        Optional<Post> optionalPost = postService.getById(id);
        if(optionalPost.isPresent()){
            Post existingPost = optionalPost.get();
            existingPost.setTitle(post.getTitle());
            existingPost.setShortDescription(post.getShortDescription());
            existingPost.setBody(post.getBody());

            try {
                fileService.save(file);
                existingPost.setImageFilePath(file.getOriginalFilename());
            } catch (Exception e){
                logger.error("Error processing file: {}", file.getOriginalFilename());
            }
            postService.save(existingPost);
        }
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/{id}/delete")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String deletePost(@PathVariable Long id){
        // find post by id
        Optional<Post>  optionalPost = postService.getById(id);
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            postService.delete(post);
            return "redirect:/blog";
        } else {
            return "p404";
        }
    }

}
