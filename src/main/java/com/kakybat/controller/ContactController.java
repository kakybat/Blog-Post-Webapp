package com.kakybat.controller;

import com.kakybat.constants.AppConstants;
import com.kakybat.model.Contact;
import com.kakybat.model.User;
import com.kakybat.service.ContactService;
import com.kakybat.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ContactController {
    public static final Logger log = LoggerFactory.getLogger(ContactController.class);

    private final ContactService contactService;
    private final UserService userService;

    public ContactController(ContactService contactService, UserService userService){
        this.contactService = contactService;
        this.userService = userService;
    }

    @RequestMapping("/contact")
    @PreAuthorize("isAnonymous()")
    public String displayContactPage(Model model, Principal principal){
        setUserModelAttribute(model, principal);
        model.addAttribute("pageTitle", "Contact Me");
        model.addAttribute("contact", new Contact());
        return "contact";
    }

    @RequestMapping(value = "/saveMessage", method = POST)
    public String saveMessage(@Valid @ModelAttribute("contact") Contact contact, Errors errors, Model model, Principal principal){
        setUserModelAttribute(model, principal);
        if(errors.hasErrors()){
            log.error("Contact form validation failed due to : " + errors.toString());
            return "contact";
        }
        contactService.saveMessageDetails(contact);
        return "redirect:/contact";
    }

    @RequestMapping("/displayMessages")
    public ModelAndView displayMessages(Model model, Principal principal){
        setUserModelAttribute(model, principal);
        List<Contact> contactMessages = contactService.findMessageWithOpenStatus();
        ModelAndView modelAndView = new ModelAndView("messages");
        modelAndView.addObject("contactMessages", contactMessages);
        modelAndView.addObject("pageTitle", "Contact Messages");
        return modelAndView;
    }

    @RequestMapping(value = "/closeMessage", method = GET)
    public String closeMessage(@RequestParam int id, Authentication authentication, Model model){
        setUserModelAttribute(model, authentication);
        contactService.updateMessageStatus(id, authentication.getName());
        return "redirect:/displayMessages";
    }

    private void setUserModelAttribute(Model model, Principal principal){
        if(principal != null){
            String email = principal.getName();
            User user = userService.findUserByEmail(email);
            model.addAttribute("user", user);
        }
    }
}
