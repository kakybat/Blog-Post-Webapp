package com.kakybat.service;

import com.kakybat.model.Person;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class UserAttributeService {

   private final PersonService personService;
   UserAttributeService(PersonService personService) {
       this.personService = personService;
   }

    public void setUserModelAttribute(Model model, Authentication authentication){
       if(authentication != null){
           String email = authentication.getName();
           Person person = personService.findUserByEmail(email);
           model.addAttribute("person", person);
       }
    }
}
