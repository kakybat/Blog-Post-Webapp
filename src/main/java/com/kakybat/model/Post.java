package com.kakybat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Post title must not be blank")
    @Size(min = 10, max = 120, message = "Post title must be at least 10 characters long")
    private String title;

    @NotBlank(message = "Short description must not be blank")
    @Size(min = 10, max = 180, message = "Short description should be between 20 and 180 characters")
    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "Post content must not be blank")
    @Size(min = 200, message = "Post content must be at least 200 characters long")
    private String body;

    private String imageFilePath;


    @ManyToOne(fetch = FetchType.EAGER)//, cascade = CascadeType.PERSIST, targetEntity = User.class
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
    private Person person;


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public Post() {
    }

    public Post(Long id, String title, String shortDescription, String body, String imageFilePath, Person person, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.shortDescription = shortDescription;
        this.body = body;
        this.imageFilePath = imageFilePath;
        this.person = person;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Post title must not be blank") @Size(min = 10, max = 120, message = "Post title must be at least 10 characters long") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "Post title must not be blank") @Size(min = 10, max = 120, message = "Post title must be at least 10 characters long") String title) {
        this.title = title;
    }

    public @NotBlank(message = "Short description must not be blank") @Size(min = 10, max = 180, message = "Short description should be between 20 and 100 characters") String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(@NotBlank(message = "Short description must not be blank") @Size(min = 10, max = 180, message = "Short description should be between 20 and 100 characters") String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public @NotBlank(message = "Post content must not be blank") @Size(min = 200, message = "Post content must be at least 200 characters long") String getBody() {
        return body;
    }

    public void setBody(@NotBlank(message = "Post content must not be blank") @Size(min = 200, message = "Post content must be at least 200 characters long") String body) {
        this.body = body;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Post{" +
                "body='" + body + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", imageFilePath='" + imageFilePath + '\'' +
                ", person=" + person +
                ", comments=" + comments +
                '}';
    }
}
