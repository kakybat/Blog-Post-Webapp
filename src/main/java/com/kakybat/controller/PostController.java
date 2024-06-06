package com.kakybat.controller;

import com.kakybat.model.Comment;
import com.kakybat.model.User;
import com.kakybat.model.Post;
import com.kakybat.service.CommentService;
import com.kakybat.service.FileService;
import com.kakybat.serviceImpl.UserServiceImpl;
import com.kakybat.serviceImpl.PostService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
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
    @PreAuthorize("isAnonymous()")
    public String getPostList(Model model, Principal principal){
        setUserModelAttribute(model, principal);
        List<Post> posts = postService.getAll();
        model.addAttribute("pageTitle", "Blog");
        model.addAttribute("posts", posts);
        return "blog";
    }

    @GetMapping("/posts/{postId}")
    @PreAuthorize("isAnonymous()")
    public String getSinglePost(@PathVariable Long postId, Model model, Principal principal){
        setUserModelAttribute(model, principal);
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
    @PreAuthorize("isAnonymous()")
    public String createNewPost(Model model, Principal principal){
        setUserModelAttribute(model, principal);
        Post post = new Post();
        model.addAttribute("post", post);
        return "new_post";
    }
    @RequestMapping(value = "/posts/save", method = {RequestMethod.POST})
    public String saveNewPost(@Valid @ModelAttribute("post") Post post, Errors errors, Model model, Principal principal, MultipartFile file){
        setUserModelAttribute(model, principal);
        if(errors.hasErrors()){
            return "new_post";
        } else {
            String authUsername = "anonymousUser";
            if(principal != null){
                authUsername = principal.getName();
                User user = userService.findUserByEmail(authUsername);
                model.addAttribute("user", user);
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
    }
    @RequestMapping("/posts/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String getPostForEdit(@PathVariable Long id, Model model, Principal principal){
        setUserModelAttribute(model, principal);
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
    public String updatePost(@Valid @ModelAttribute("post")  Post post, Errors errors, @PathVariable Long id, Model model, Principal principal, @RequestParam("file") MultipartFile file){
        setUserModelAttribute(model, principal);
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

    private void setUserModelAttribute(Model model, Principal principal){
        if(principal != null){
            String email = principal.getName();
            User user = userService.findUserByEmail(email);
            model.addAttribute("user", user);
        }
    }

}
