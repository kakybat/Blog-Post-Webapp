package com.kakybat.controller;

import com.kakybat.dto.UserDto;
// import com.kakybat.model.Comment;
import com.kakybat.model.Post;
import com.kakybat.model.User;
import com.kakybat.service.FileService;
import com.kakybat.service.UserService;
import com.kakybat.serviceImpl.PostService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    public static Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final PostService postService;
    private final FileService fileService;
    public UserController(UserService userService, PostService postService, FileService fileService){
        this.userService = userService;
        this.postService = postService;
        this.fileService = fileService;
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
    @PreAuthorize("isAuthenticated()")
    public String getSinglePost(@PathVariable Long postId, Model model, Principal principal){
        setUserModelAttribute(model, principal);
        Optional<Post> optionalPost = postService.getById(postId);
        // List<Comment> comments = commentService.getAllCommentsForPost(postId);
        if(optionalPost.isPresent()){
            Post myPost = optionalPost.get();
//            model.addAttribute("post", post);
            model.addAttribute("myPost", myPost);
            // model.addAttribute("comments", comments);
            return "my_post";
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
        setUserModelAttribute(model, principal);
        model.addAttribute("pageTitle", "Edit User Details");
        return "edit_me";
    }

    @PostMapping("/update_me")
    @PreAuthorize("isAuthenticated()")
    public String updateMe(Model model, Principal principal, @RequestParam("file") MultipartFile file){
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        try {
            fileService.save(file);
            user.setImageFilePath(file.getOriginalFilename());
        } catch (Exception e){
            logger.error("Error processing file: {}", file.getOriginalFilename());
        }
        userService.updateUser(user);

        return "redirect:/dashboard";
    }


    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public User findUserByEmail(String email){
        return userService.findUserByEmail(email);
    }
    @GetMapping("/users")
    public String getAllUsers(Model model, Principal principal){
        setUserModelAttribute(model, principal);
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    private void setUserModelAttribute(Model model, Principal principal){
        if(principal != null){
            String email = principal.getName();
            User user = userService.findUserByEmail(email);
            model.addAttribute("user", user);
        }
    }

}