package com.kakybat.controller;

import com.kakybat.dto.UserDto;
// import com.kakybat.model.Comment;
import com.kakybat.model.Post;
import com.kakybat.model.User;
import com.kakybat.service.UserService;
import com.kakybat.serviceImpl.PostService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    private final UserService userService;
    private final PostService postService;
    public UserController(UserService userService, PostService postService){
        this.userService = userService;
        this.postService = postService;
    }
    @GetMapping("/dashboard")
    public String getDashboardPage(Model model, Principal principal){
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        List<Post> myPosts = postService.getMyPosts(user);
        model.addAttribute("pageTitle", "Dashboard");
        model.addAttribute("user", user);
        model.addAttribute("myPosts", myPosts);


        return "dashboard";
    }


    @GetMapping("/myPosts/{postId}")
    public String getSinglePost(@PathVariable Long postId, Model model){
        Optional<Post> optionalPost = postService.getById(postId);
        // List<Comment> comments = commentService.getAllCommentsForPost(postId);
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            // model.addAttribute("comments", comments);
            return "post";
        } else {
            return "p404";
        }
    }

    @PostMapping("/signup")
    public String registerNewUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());
        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null, "There is already an account registered with the same email");
        }
        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/signup";
        }
        userService.saveUser(userDto);
        return "redirect:/signup?success";
    }

    @GetMapping("/edit-user-details")
    @PreAuthorize("isAuthenticated()")
    public String selfUserEdit(Model model, Principal principal){
        String userEmail = principal.getName();
        User user = userService.findUserByEmail(userEmail);

        model.addAttribute("pageTitle", "Edit User Details");
        model.addAttribute("user", user);
        return "edit_me";
    }
    @GetMapping("/user")
    public User findUserByEmail(String email){
        return userService.findUserByEmail(email);
    }
    @GetMapping("/users")
    public String getAllUsers(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }



}