package com.kakybat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Address extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    private Long addressId;

    @NotBlank(message = "Address1 must not be blank")
    @Size(min = 5, message = "Address1 must be at least 5 characters long")
    private String address1;

    private String Address2;

    @NotBlank(message = "City must not be blank")
    @Size(min = 5, message = "City must be at least 5 character long")
    private String city;

    @NotBlank(message = "State must not be blank")
    @Size(min = 5, message = "State must not be at least 5 characters long")
    private String state;

    @NotBlank(message = "Zip code must not be blank")
    @Pattern(regexp = "(^$|[0-9]{5})", message = "Zip code must be 5 digits")
    private String zipCode;

    public Address(){}
    public Address(String address1, String address2, String city, String state, String zipCode) {
        this.address1 = address1;
        this.Address2 = address2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public @NotBlank(message = "Address1 must not be blank") @Size(min = 5, message = "Address1 must be at least 5 characters long") String getAddress1() {
        return address1;
    }

    public void setAddress1(@NotBlank(message = "Address1 must not be blank") @Size(min = 5, message = "Address1 must be at least 5 characters long") String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return Address2;
    }

    public void setAddress2(String address2) {
        Address2 = address2;
    }

    public @NotBlank(message = "City must not be blank") @Size(min = 5, message = "City must be at least 5 character long") String getCity() {
        return city;
    }

    public void setCity(@NotBlank(message = "City must not be blank") @Size(min = 5, message = "City must be at least 5 character long") String city) {
        this.city = city;
    }

    public @NotBlank(message = "State must not be blank") @Size(min = 5, message = "State must not be at least 5 characters long") String getState() {
        return state;
    }

    public void setState(@NotBlank(message = "State must not be blank") @Size(min = 5, message = "State must not be at least 5 characters long") String state) {
        this.state = state;
    }

    @NotBlank(message = "Zip code must not be blank")
    @Pattern(regexp = "(^$|[0-9]{5})", message = "Zip code must be 5 digits")
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(@NotBlank(message = "Zip code must not be blank") @Pattern(regexp = "(^$|[0-9]{5})", message = "Zip code must be 5 digits") String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", address1='" + address1 + '\'' +
                ", Address2='" + Address2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode=" + zipCode +
                '}';
    }
}
