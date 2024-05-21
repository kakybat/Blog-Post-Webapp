package com.kakybat.service;

import com.kakybat.constants.AppConstants;
import com.kakybat.model.Contact;

import com.kakybat.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        contact.setCreatedBy(AppConstants.ANONYMOUS);
        contact.setCreatedAt(LocalDateTime.now());
        Contact savedContact = contactRepository.save(contact);
        if(savedContact.getContactId() > 0){
            isSaved = true;
        }
        return isSaved;
    }

    public List<Contact> findMessageWithOpenStatus(){
        return contactRepository.findByStatus(AppConstants.OPEN);
    }

    public boolean updateMessageStatus(int contactId, String updatedBy){
        boolean isUpdated = false;
        Optional<Contact> contact = contactRepository.findById(contactId);
        contact.ifPresent(contact1 -> {
            contact1.setStatus(AppConstants.CLOSE);
            contact1.setUpdatedBy(updatedBy);
            contact1.setUpdatedAt(LocalDateTime.now());
        });
        Contact updatedContact = contactRepository.save(contact.get());
        if(updatedContact.getUpdatedBy() != null){
            isUpdated = true;
        }
        return isUpdated;
    }

}
