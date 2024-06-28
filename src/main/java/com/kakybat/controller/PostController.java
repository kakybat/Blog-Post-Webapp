package com.kakybat.controller;

import com.kakybat.model.Comment;
import com.kakybat.model.Person;
import com.kakybat.model.Post;
import com.kakybat.repository.CommentRepository;
import com.kakybat.service.FileService;
import com.kakybat.service.PersonService;
import com.kakybat.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.security.core.Authentication;
import java.util.List;
import java.util.Optional;

@Controller
public class PostController {
    public static Logger logger = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;
    private final PersonService personService;
    private final FileService fileService;
//    private final CommentService commentService;
    @Autowired
    CommentRepository commentRepository;

    public PostController(PostService postService, PersonService personService, FileService fileService){
        this.postService = postService;
        this.personService = personService;
        this.fileService = fileService;

    }

    @GetMapping("/blog")
    @PreAuthorize("isAnonymous()")
    public String getPostList(Model model, Authentication auth){
        setUserModelAttribute(model, auth);
        List<Post> posts = postService.getAll();
        model.addAttribute("pageTitle", "Blog");
        model.addAttribute("posts", posts);
        return "blog";
    }

    @GetMapping("/posts/{postId}")
    @PreAuthorize("isAnonymous()")
    public String getSinglePost(@PathVariable Long postId, Model model, Authentication auth){
        setUserModelAttribute(model, auth);

        Comment newComment = new Comment();
        model.addAttribute("newComment", newComment);

        Optional<Post> optionalPost = postService.getById(postId);

        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            return "post";
        } else {
            return "error";
        }
    }
    @GetMapping("/posts/new")
    @PreAuthorize("isAnonymous()")
    public String createNewPost(Model model, Authentication auth){
        setUserModelAttribute(model, auth);
        Post post = new Post();
        model.addAttribute("post", post);
        return "new_post";
    }
    @RequestMapping(value = "/posts/save", method = {RequestMethod.POST})
    public String saveNewPost(@Valid @ModelAttribute("post") Post post, Errors errors, Model model, Authentication auth, MultipartFile file){
        setUserModelAttribute(model, auth);
        if(errors.hasErrors()){
            return "new_post";
        } else {
            String authUsername = "anonymousUser";
            if(auth != null){
                authUsername = auth.getName();
                Person person = personService.findUserByEmail(authUsername);
                model.addAttribute("user", person);
            } else {
                throw new IllegalArgumentException("Account not found");
            }
            Person person = personService.findUserByEmail(authUsername);
            try{
                fileService.save(file);
                post.setImageFilePath(file.getOriginalFilename());
            } catch (Exception e){
                logger.error("Error processing file: {}", file.getOriginalFilename());
            }

            post.setPerson(person);
            postService.save(post);
            return "redirect:/posts/" + post.getId();
        }
    }
    @RequestMapping("/posts/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String getPostForEdit(@PathVariable Long id, Model model, Authentication auth){
        setUserModelAttribute(model, auth);
        Optional<Post> optionalPost = postService.getById(id);
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            return "post_edit";
        } else {
            return "error";
        }
    }

    @PostMapping("/posts/{id}")
    @PreAuthorize("isAuthenticated()")
    public String updatePost(@Valid @ModelAttribute("post") Post post, Errors errors, @PathVariable Long id, Model model, Authentication auth, @RequestParam("file") MultipartFile file){
        setUserModelAttribute(model, auth);
        if(errors.hasErrors()){
            Optional<Post> optionalPost = postService.getById(id);
            if(optionalPost.isPresent()){
                Post postToUpdate = optionalPost.get();
                model.addAttribute("post", postToUpdate);
            }

            return "post_edit";
        } else {
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
            return "error";
        }
    }

    @PostMapping("/posts/{postId}/comments")
    public String addComment(@PathVariable Long postId, @ModelAttribute("newComment") Comment newComment, Model model, Authentication auth) {
        setUserModelAttribute(model, auth);
        Post post = postService.findById(postId);
        Person person = personService.findUserByEmail(auth.getName());
        newComment.setPerson(person);
        newComment.setPost(post);
        commentRepository.save(newComment);
        return "redirect:/posts/" + postId;
    }
    @GetMapping("/comments/deleteComment/{commentId}")
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



    private void setUserModelAttribute(Model model, Authentication auth){
        if(auth != null){
            String email = auth.getName();
            Person person = personService.findUserByEmail(email);
            model.addAttribute("person", person);
        }
    }

}
