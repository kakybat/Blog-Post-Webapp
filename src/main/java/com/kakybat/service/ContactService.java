package com.kakybat.service;

import com.kakybat.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    private final static Logger log = LoggerFactory.getLogger(ContactService.class);

    public boolean saveMessageDetails(Contact contact){
        boolean isSaved = true;
        //  TODO - Need to persist the data the DB table
        log.info(contact.toString());
        return isSaved;
    }
}
