package com.kakybat.controller;

import com.kakybat.model.Address;
import com.kakybat.model.Person;
import com.kakybat.model.Profile;
import com.kakybat.repository.PersonRepository;
import com.kakybat.service.PersonService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfileController {


    private final PersonService personService;
    private final PersonRepository personRepository;

    public ProfileController(PersonService personService, PersonRepository personRepository) {
        this.personService = personService;
        this.personRepository = personRepository;
    }

    @RequestMapping("/displayProfile")
    public ModelAndView displayProfile(Model model, Authentication auth, HttpSession session){
        model.addAttribute("pageTitle", "Profile");
        setUserModelAttributes(model, auth);

        Person person = (Person) session.getAttribute("loggedUser");
        Profile profile = new Profile();
        profile.setName(person.getName());
        profile.setEmail(person.getEmail());

        if(person.getAddress() != null && person.getAddress().getAddressId() >0){
            profile.setAddress1(person.getAddress().getAddress1());
            profile.setAddress2(person.getAddress().getAddress2());
            profile.setCity(person.getAddress().getCity());
            profile.setState(person.getAddress().getState());
            profile.setZipCode(person.getAddress().getZipCode());
        }

        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("profile", profile);
        return modelAndView;
    }


    @PostMapping(value = "/updateProfile")
    public String updateProfile(@Valid @ModelAttribute("profile") Profile profile, Errors errors, Model model, Authentication auth, HttpSession session){
        setUserModelAttributes(model, auth);
        if(errors.hasErrors()){
            return "profile";
        }
        Person person = (Person) session.getAttribute("loggedUser");
        person.setName(profile.getName());
        person.setEmail(profile.getEmail());
        if(person.getAddress() == null || !(person.getAddress().getAddressId() > 0)){
            person.setAddress(new Address());
        }
        person.getAddress().setAddress1(profile.getAddress1());
        person.getAddress().setAddress2(profile.getAddress2());
        person.getAddress().setCity(profile.getCity());
        person.getAddress().setState(profile.getState());
        person.getAddress().setZipCode(profile.getZipCode());
        personRepository.save(person);
        session.setAttribute("loggedUser", person);
        return "redirect:/displayProfile";
    }


    private void setUserModelAttributes(Model model, Authentication auth){
        if(auth != null){
            String email = auth.getName();
            Person person = personService.findUserByEmail(email);
            model.addAttribute("person", person);
        }
    }
}
