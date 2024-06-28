package com.kakybat.controller;

import com.kakybat.model.Comment;
import com.kakybat.model.Person;
import com.kakybat.model.Post;
import com.kakybat.repository.CommentRepository;
import com.kakybat.repository.PersonRepository;
import com.kakybat.service.FileService;
import com.kakybat.service.PersonService;
import com.kakybat.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import org.springframework.security.core.Authentication;
import java.util.List;
import java.util.Optional;

@Controller
public class DashboardController {
    public static Logger logger = LoggerFactory.getLogger(DashboardController.class);
    private final PersonService personService;
    private final PostService postService;
    private final FileService fileService;
    private final CommentRepository commentRepository;
    public DashboardController(PersonService personService, PostService postService, FileService fileService, CommentRepository commentRepository){
        this.personService = personService;
        this.postService = postService;
        this.fileService = fileService;
        this.commentRepository = commentRepository;
    }
    @Autowired
    PersonRepository personRepository;

    @RequestMapping("/dashboard")
    public String getDashboardPage(Model model, Authentication auth, HttpSession session){
        String email = auth.getName();
        Person person = personService.findUserByEmail(email);

        List<Post> myPosts = postService.getMyPosts(person);
        model.addAttribute("pageTitle", "Dashboard");
        model.addAttribute("person", person);
        model.addAttribute("username", person.getName());
        model.addAttribute("role", auth.getAuthorities().toString());
        session.setAttribute("loggedUser", person);
        model.addAttribute("myPosts", myPosts);


        return "dashboard";
    }


    @GetMapping("/myPosts/{postId}")
    @PreAuthorize("isAuthenticated()")
    public String getSinglePost(@PathVariable Long postId, Model model, Authentication auth){
        setUserModelAttribute(model, auth);
        Comment newComment = new Comment();
        model.addAttribute("newComment", newComment);

        Optional<Post> optionalPost = postService.getById(postId);
        if(optionalPost.isPresent()){
            Post myPost = optionalPost.get();
            model.addAttribute("myPost", myPost);
            return "my_post";
        } else {
            return "p404";
        }
    }


    @PostMapping("/myPosts/{postId}/comments")
    public String addComment(@PathVariable Long postId, @ModelAttribute("newComment") Comment newComment, Model model, Authentication auth) {
        setUserModelAttribute(model, auth);
        Post post = postService.findById(postId);
        Person person = personService.findUserByEmail(auth.getName());
        newComment.setPerson(person);
        newComment.setPost(post);
        commentRepository.save(newComment);
        return "redirect:/myPosts/" + postId;
    }
    @GetMapping("/myPostComments/deleteComment/{commentId}")
    public String deleteComment(@PathVariable Long commentId, Model model, Authentication auth) {
        setUserModelAttribute(model, auth);
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        Post post = comment.getPost();
        postService.save(post);
        if(auth.getName().equals(comment.getPerson().getEmail()) || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            commentRepository.delete(comment);
            return "redirect:/posts/" + post.getId();
        } else {
            throw new EntityNotFoundException("Comment not found");
        }
    }


    @GetMapping("/edit-user-details")
    @PreAuthorize("isAuthenticated()")
    public String selfUserEdit(Model model, Authentication auth){
        setUserModelAttribute(model, auth);
        model.addAttribute("pageTitle", "Edit User Details");
        return "edit_me";
    }

    @PostMapping("/update_me")
    @PreAuthorize("isAuthenticated()")
    public String updateMe(Model model, Authentication auth, @RequestParam("file") MultipartFile file){
        setUserModelAttribute(model, auth);
        String email = auth.getName();
        Person person = personService.findUserByEmail(email);
        model.addAttribute("person", person);
        try {
            fileService.save(file);
            person.setImageFilePath(file.getOriginalFilename());
        } catch (Exception e){
            logger.error("Error processing file: {}", file.getOriginalFilename());
        }
        personService.updateUser(person);

        return "redirect:/dashboard";
    }


    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public Person findUserByEmail(String email){
        return personService.findUserByEmail(email);
    }


    @GetMapping("/users")
    public String getAllUsers(Model model, Authentication auth){
        setUserModelAttribute(model, auth);
        model.addAttribute("pageTitle", "User Details");
            List<Person> people = personService.findAllUsers();
            model.addAttribute("users", people);
        return "users";
    }

    private void setUserModelAttribute(Model model, Authentication auth){
        if(auth != null){
            String email = auth.getName();
            Person person = personService.findUserByEmail(email);
            model.addAttribute("person", person);
        }
    }

}