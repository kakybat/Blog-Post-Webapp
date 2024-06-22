package com.kakybat.service;

import com.kakybat.constants.AppConstants;
import com.kakybat.model.Person;
import com.kakybat.model.Role;
import com.kakybat.repository.RoleRepository;
import com.kakybat.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonService(PersonRepository personRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public boolean createNewUser(Person person){
        boolean isSaved = false;
        Role role = roleRepository.getByRoleName(AppConstants.AUTHOR_ROLE);
        person.setRole(role);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person = personRepository.save(person);
        if(person.getUserId() > 0){
            isSaved = true;
        }
        return isSaved;
    }

    public void updateUser(Person person){
        person.setName(person.getName());
        person.setPassword(person.getPassword());
        personRepository.save(person);
    }

    public Person findUserByEmail(String email){
        return personRepository.findByEmail(email);
    }

    public List<Person> findAllUsers(){
        List<Person> people = personRepository.findAll();
        return new ArrayList<>(people);
    }

    public void deleteUser(Person person){
        personRepository.delete(person);
    }
}
