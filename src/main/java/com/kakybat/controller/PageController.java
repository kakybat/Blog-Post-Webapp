package com.kakybat.controller;

import com.kakybat.dto.UserDto;
import com.kakybat.model.User;
import com.kakybat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class PageController {
    private final UserService userService;
    @Autowired
    public PageController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/")
    @PreAuthorize("isAnonymous()")
    public String getIndexPage(Model model, Principal principal){
        setUserModelAttribute(model, principal);
        return "index";
    }

    @GetMapping("/about")
    @PreAuthorize("isAnonymous()")
    public String getAboutPage(Model model, Principal principal){
        setUserModelAttribute(model, principal);
        model.addAttribute("pageTitle", "About Me");
        return "about";
    }
    @GetMapping("/p404")
    public String get404Page(Model model){
        model.addAttribute("pageTitle", "404 Page");
        return "p404";
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public String getLoginPage(Model model, Principal principal){
        setUserModelAttribute(model, principal);
        model.addAttribute("pageTitle", "Login");
        model.addAttribute("user", new UserDto());
        return "/login";
    }
    @GetMapping("/signup")
    @PreAuthorize("isAnonymous()")
    public String showSignupForm(Model model, Principal principal){
        setUserModelAttribute(model, principal);
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "Register");
        return "signup";
    }

    private void setUserModelAttribute(Model model, Principal principal){
        if(principal != null){
            String email = principal.getName();
            User user = userService.findUserByEmail(email);
            model.addAttribute("user", user);
        }
    }

}
