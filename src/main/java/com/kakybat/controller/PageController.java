package com.kakybat.controller;

import com.kakybat.model.Person;
import com.kakybat.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class PageController {
    private final PersonService personService;
    @Autowired
    public PageController(PersonService personService){
        this.personService = personService;
    }

    @RequestMapping(value = {"/", "home"}, method = {RequestMethod.GET})
    @PreAuthorize("isAnonymous()")
    public String getIndexPage(Model model,Authentication auth){
        setUserModelAttribute(model, auth);
        return "index";
    }

    @RequestMapping(value = "/about", method = {RequestMethod.GET})
    @PreAuthorize("isAnonymous()")
    public String getAboutPage(Model model, Authentication auth){
        setUserModelAttribute(model, auth);
        model.addAttribute("pageTitle", "About Me");
        return "about";
    }

    private void setUserModelAttribute(Model model, Authentication auth){
        if(auth != null){
            String email = auth.getName();
            Person person = personService.findUserByEmail(email);
            model.addAttribute("person", person);
        }
    }

}
