package com.kakybat.controller;

import com.kakybat.model.Person;
import com.kakybat.service.PersonService;
import com.kakybat.service.UserAttributeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("public")
public class PublicController {

    @Autowired
    PersonService personService;
    @Autowired
    UserAttributeService userAttributeService;

    @RequestMapping(value ="/register",method = { RequestMethod.GET})
    public String displayRegisterPage(@RequestParam(value = "register", required = false) String register, Model model, Authentication auth) {
        userAttributeService.setUserModelAttribute(model, auth);
        String registerMessage = null;
        if(register != null){
            registerMessage = "You have been registered successfully, please log in";
        }
        model.addAttribute("person", new Person());
        model.addAttribute("registerMessage", registerMessage);
        return "register";
    }

    @RequestMapping(value ="/createUser",method = { RequestMethod.POST})
    public String createUser(@Valid @ModelAttribute("person") Person person, Errors errors, Model model, Authentication auth) {
        userAttributeService.setUserModelAttribute(model, auth);
        if(errors.hasErrors()){
            return "register";
        }
        boolean isSaved = personService.createNewUser(person);
        if(isSaved){
            return "redirect:/public/register?register=true";
        }else {
            return "register";
        }
    }
}
