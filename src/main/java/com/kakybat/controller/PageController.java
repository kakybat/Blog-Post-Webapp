package com.kakybat.controller;

import com.kakybat.dto.UserDto;
import com.kakybat.model.User;
import com.kakybat.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class PageController {
    private final UserService userService;
    @Autowired
    public PageController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = {"/", "home"})
    @PreAuthorize("isAnonymous()")
    public String getIndexPage(Model model, Principal principal){
        setUserModelAttribute(model, principal);
        return "index";
    }

    @RequestMapping("/about")
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

    //@GetMapping("/login")
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    @PreAuthorize("isAnonymous()")
    public String getLoginPage(@RequestParam(value = "error", required = false) String error,
                                   @RequestParam(value = "logout", required = false) String logout,
                                   Model model, Principal principal){
        setUserModelAttribute(model, principal);

        String errorMessage = null;
        String logoutMessage = null;
        if(error != null){
            errorMessage = "Username or Password is incorrect";
        }
        if(logout != null){
            logoutMessage = "You have been logged out successfully";
        }
        model.addAttribute("pageTitle", "Login");
        model.addAttribute("user", new UserDto());
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("logoutMessage", logoutMessage);
        return "/login";
    }
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(Model model, Principal principal, HttpServletRequest request, HttpServletResponse response){
        setUserModelAttribute(model, principal);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";

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
