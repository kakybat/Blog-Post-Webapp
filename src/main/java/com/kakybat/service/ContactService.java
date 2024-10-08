package com.kakybat.service;

import com.kakybat.constants.AppConstants;
import com.kakybat.model.Contact;

import com.kakybat.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository){
        this.contactRepository = contactRepository;
    }

    public boolean saveMessageDetails(Contact contact){
        boolean isSaved = false;
        contact.setStatus(AppConstants.OPEN);
        Contact savedContact = contactRepository.save(contact);
        if(savedContact.getContactId() > 0){
            isSaved = true;
        }
        return isSaved;
    }

    public List<Contact> findMessageWithOpenStatus(){
        return contactRepository.findByStatus(AppConstants.OPEN);
    }

    public boolean updateMessageStatus(int contactId){
        boolean isUpdated = false;
        Optional<Contact> contact = contactRepository.findById(contactId);
        contact.ifPresent(contact1 -> {
            contact1.setStatus(AppConstants.CLOSE);
        });
        Contact updatedContact = contactRepository.save(contact.get());
        if(updatedContact.getUpdatedBy() != null){
            isUpdated = true;
        }
        return isUpdated;
    }

}
