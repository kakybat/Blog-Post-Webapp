package com.kakybat.model;


import com.kakybat.annotation.EmailMatch;
//import com.kakybat.annotation.FieldsValueMatch;
import com.kakybat.annotation.PasswordMatch;
//import com.kakybat.annotation.PasswordValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@EmailMatch
@PasswordMatch
//@FieldsValueMatch.List({
//        @FieldsValueMatch(
//                field = "password",
//                fieldMatch = "confirmPassword",
//                message = "Passwords do not match!"
//        ),
//        @FieldsValueMatch(
//                field = "email",
//                fieldMatch = "confirmEmail",
//                message = "Email address do not match!"
//        )
//})

public class Person extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    private Long userId;

    @NotBlank(message = "Name must not be blank")
    @Size(min = 3, max = 50, message = "Name should be between 3 and 50 characters")
    private String name;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Confirm Email must not be blank")
    @Email(message = "Please provide a valid confirm email address")
    @Transient
    private String confirmEmail;


    @NotBlank(message = "Password must not be blank")
    @Size(min = 5, message = "Password must be at least 5 characters long")
//    @PasswordValidator
    private String password;

    @NotBlank(message = "Confirm Password must not be blank")
    @Size(min = 5, message = "Confirm Password must be at least 5 characters long")
    @Transient
    private String confirmPassword;


    private String imageFilePath;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, targetEntity = Role.class)
    @JoinColumn(name = "role_id", referencedColumnName = "roleId", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)

    private List<Post> posts = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Address.class)
    @JoinColumn(name = "address_id", referencedColumnName = "addressId", nullable = true)
    private Address address;

    public Person() {
    }

    public Person(Long userId, String name, String email, String confirmEmail, String password, String confirmPassword, String imageFilePath, Role role, List<Post> posts, Address address) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.confirmEmail = confirmEmail;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.imageFilePath = imageFilePath;
        this.role = role;
        this.posts = posts;
        this.address = address;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public @NotBlank(message = "Name must not be blank") @Size(min = 3, max = 50, message = "Name should be between 3 and 50 characters") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name must not be blank") @Size(min = 3, max = 50, message = "Name should be between 3 and 50 characters") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Email must not be blank") @Email(message = "Please provide a valid email address") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email must not be blank") @Email(message = "Please provide a valid email address") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Confirm Email must not be blank") @Email(message = "Please provide a valid confirm email address") String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(@NotBlank(message = "Confirm Email must not be blank") @Email(message = "Please provide a valid confirm email address") String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }

    public @NotBlank(message = "Password must not be blank") @Size(min = 5, message = "Password must be at least 5 characters long") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password must not be blank") @Size(min = 5, message = "Password must be at least 5 characters long") String password) {
        this.password = password;
    }

    public @NotBlank(message = "Confirm Password must not be blank") @Size(min = 5, message = "Confirm Password must be at least 5 characters long") String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(@NotBlank(message = "Confirm Password must not be blank") @Size(min = 5, message = "Confirm Password must be at least 5 characters long") String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", confirmEmail='" + confirmEmail + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", imageFilePath='" + imageFilePath + '\'' +
                ", role=" + role +
                ", posts=" + posts +
                ", address=" + address +
                '}';
    }
}
