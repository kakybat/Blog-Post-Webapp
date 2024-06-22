package com.kakybat.controller;

import com.kakybat.model.Person;
import com.kakybat.service.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private final PersonService personService;
    @Autowired
    public LoginController(PersonService personService){
        this.personService = personService;
    }
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    @PreAuthorize("isAnonymous()")
    public String getLoginPage(@RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "logout", required = false) String logout,
//                               @RequestParam(value = "register", required = false) String register,
                               Model model, Authentication auth){
        setUserModelAttribute(model, auth);

        String errorMessage = null;
        String logoutMessage = null;
//        String registerMessage = null;
        if(error != null){
            errorMessage = "Username or Password is incorrect";
        }
        else if(logout != null){
            logoutMessage = "You have been logged out successfully";
        }
//        else if(register != null){
//            registerMessage = "You have been registered successfully, please log in";
//        }
        model.addAttribute("pageTitle", "Login");
        model.addAttribute("person", new Person());
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("logoutMessage", logoutMessage);
//        model.addAttribute("registerMessage", registerMessage);
        return "/login";
    }
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(Model model, Authentication auth, HttpServletRequest request, HttpServletResponse response){
        setUserModelAttribute(model, auth);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout=true";

    }

    private void setUserModelAttribute(Model model, Authentication auth){
        if(auth != null){
            String email = auth.getName();
            Person person = personService.findUserByEmail(email);
            model.addAttribute("person", person);
        }
    }
}
