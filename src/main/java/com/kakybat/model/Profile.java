package com.kakybat.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Profile {
    @NotBlank(message = "Name must not be blank")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    @NotBlank(message = "? must not be blank")
    @Email(message = "Please provide a valid email address")
    private String email;


    @NotBlank(message = "Address1 must not be blank")
    @Size(min = 5, message = "Address1 must be at least 5 characters long")
    private String address1;

    private String address2;

    @NotBlank(message = "City must not be blank")
    @Size(min = 5, message = "City must be at least 5 characters long")
    private String city;

    @NotBlank(message = "State must not be blank")
    @Size(min = 5, message = "State must be at least 5 characters long")
    private String state;

    @NotBlank(message = "Zip Code must not be blank")
    @Pattern(regexp = "(^$|[0-9]{5})", message = "Zip Code must be 5 digits")
    private String zipCode;

    public Profile() {
    }

    public Profile(String state, String name, String email, String address1, String address2, String city, String zipCode) {
        this.state = state;
        this.name = name;
        this.email = email;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.zipCode = zipCode;
    }

    public @NotBlank(message = "Name must not be blank") @Size(min = 3, message = "Name must be at least 3 characters long") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name must not be blank") @Size(min = 3, message = "Name must be at least 3 characters long") String name) {
        this.name = name;
    }

    public @NotBlank(message = "? must not be blank") @Email(message = "Please provide a valid email address") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "? must not be blank") @Email(message = "Please provide a valid email address") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Address1 must not be blank") @Size(min = 5, message = "Address1 must be at least 5 characters long") String getAddress1() {
        return address1;
    }

    public void setAddress1(@NotBlank(message = "Address1 must not be blank") @Size(min = 5, message = "Address1 must be at least 5 characters long") String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public @NotBlank(message = "City must not be blank") @Size(min = 5, message = "City must be at least 5 characters long") String getCity() {
        return city;
    }

    public void setCity(@NotBlank(message = "City must not be blank") @Size(min = 5, message = "City must be at least 5 characters long") String city) {
        this.city = city;
    }

    public @NotBlank(message = "State must not be blank") @Size(min = 5, message = "State must be at least 5 characters long") String getState() {
        return state;
    }

    public void setState(@NotBlank(message = "State must not be blank") @Size(min = 5, message = "State must be at least 5 characters long") String state) {
        this.state = state;
    }

    public @NotBlank(message = "Zip Code must not be blank") @Pattern(regexp = "(^$|[0-9]{5})", message = "Zip Code must be 5 digits") String getZipCode() {
        return zipCode;
    }

    public void setZipCode(@NotBlank(message = "Zip Code must not be blank") @Pattern(regexp = "(^$|[0-9]{5})", message = "Zip Code must be 5 digits") String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
