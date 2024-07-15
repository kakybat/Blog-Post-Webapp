package com.kakybat.controller;

import com.kakybat.model.Contact;
import com.kakybat.service.ContactService;
import com.kakybat.service.PersonService;
import com.kakybat.service.UserAttributeService;
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

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ContactController {
    public static final Logger log = LoggerFactory.getLogger(ContactController.class);

    private final ContactService contactService;
    private final PersonService personService;
    private final UserAttributeService userAttributeService;

    public ContactController(
            ContactService contactService,
            PersonService personService,
            UserAttributeService userAttributeService
    ){
        this.contactService = contactService;
        this.personService = personService;
        this.userAttributeService = userAttributeService;
    }

    @RequestMapping("/contact")
    @PreAuthorize("isAnonymous()")
    public String displayContactPage(Model model, Authentication auth){
        userAttributeService.setUserModelAttribute(model, auth);
        model.addAttribute("pageTitle", "Contact Me");
        model.addAttribute("contact", new Contact());
        return "contact";
    }

    @RequestMapping(value = "/saveMessage", method = POST)
    public String saveMessage(@Valid @ModelAttribute("contact") Contact contact, Errors errors, Model model, Authentication auth){
        userAttributeService.setUserModelAttribute(model, auth);
        if(errors.hasErrors()){
            log.error("Contact form validation failed due to : " + errors);
            return "contact";
        }
        contactService.saveMessageDetails(contact);
        return "redirect:/contact";
    }

    @RequestMapping("/displayMessages")
    public ModelAndView displayMessages(Model model, Authentication auth){
        userAttributeService.setUserModelAttribute(model, auth);
        List<Contact> contactMessages = contactService.findMessageWithOpenStatus();
        ModelAndView modelAndView = new ModelAndView("messages");
        modelAndView.addObject("contactMessages", contactMessages);
        modelAndView.addObject("pageTitle", "Contact Messages");
        return modelAndView;
    }

    @RequestMapping(value = "/closeMessage", method = GET)
    public String closeMessage(@RequestParam int id, Authentication authentication, Model model){
        userAttributeService.setUserModelAttribute(model, authentication);
        contactService.updateMessageStatus(id);
        return "redirect:/displayMessages";
    }

//    private void setUserModelAttribute(Model model, Authentication auth){
//        if(auth != null){
//            String email = auth.getName();
//            Person person = personService.findUserByEmail(email);
//            model.addAttribute("person", person);
//        }
//    }
}
