package com.kakybat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "contact_msg")
public class Contact extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    private int contactId;

    @NotBlank(message = "Name must not be blank")
    @Size(min = 3, message = "Contact name must be at least 3 characters long")
    private String contactName;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please provide a valid email address")
    private String contactEmail;

    @NotBlank(message = "Subject must not be blank")
    @Size(min = 5, message = "Subject must be at least 5 characters long")
    private String contactSubject;

    @NotBlank(message = "Message must not be blank")
    @Size(min = 10, message = "Message must be at least 10 characters long")
    private String contactMessage;

    private String status;

    // Getter & Setters
    public int getContactId() {
        return contactId;
    }

    public @NotBlank(message = "Name must not be blank") @Size(min = 3, message = "Contact name must be at least 3 characters long") String getContactName() {
        return contactName;
    }

    public void setContactName(@NotBlank(message = "Name must not be blank") @Size(min = 3, message = "Contact name must be at least 3 characters long") String contactName) {
        this.contactName = contactName;
    }

    public @NotBlank(message = "Email must not be blank") @Email(message = "Please provide a valid email address") String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(@NotBlank(message = "Email must not be blank") @Email(message = "Please provide a valid email address") String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public @NotBlank(message = "Subject must not be blank") @Size(min = 5, message = "Subject must be at least 5 characters long") String getContactSubject() {
        return contactSubject;
    }

    public void setContactSubject(@NotBlank(message = "Subject must not be blank") @Size(min = 5, message = "Subject must be at least 5 characters long") String contactSubject) {
        this.contactSubject = contactSubject;
    }

    public @NotBlank(message = "Message must not be blank") @Size(min = 10, message = "Message must be at least 10 characters long") String getContactMessage() {
        return contactMessage;
    }

    public void setContactMessage(@NotBlank(message = "Message must not be blank") @Size(min = 10, message = "Message must be at least 10 characters long") String contactMessage) {
        this.contactMessage = contactMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // toString() method

    @Override
    public String toString() {
        return "Contact{" +
                "contactId=" + contactId +
                ", contactName='" + contactName + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", contactSubject='" + contactSubject + '\'' +
                ", contactMessage='" + contactMessage + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
