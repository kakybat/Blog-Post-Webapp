package com.kakybat.controller;

import com.kakybat.dto.UserDto;
import com.kakybat.model.User;
import com.kakybat.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PageController {
    private final UserService userService;
    @Autowired
    public PageController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/")
    public String getIndexPage(){
        return "index";
    }

    @GetMapping("/about")
    public String getAboutPage(Model model){
        model.addAttribute("pageTitle", "About Me");
        return "about";
    }
    @GetMapping("/p404")
    public String get404Page(Model model){
        model.addAttribute("pageTitle", "404 Page");
        return "p404";
    }

    @GetMapping("/contact")
    public String getContactPage(Model model){
        model.addAttribute("pageTitle", "Contact Me");
        return "contact";
    }
    @GetMapping("/login")
    public String getLoginPage(Model model){
        model.addAttribute("pageTitle", "Login");
        model.addAttribute("user", new UserDto());
        return "/login";
    }
    @GetMapping("/signup")
    public String showSignupForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "Register");
        return "signup";
    }




//
}
