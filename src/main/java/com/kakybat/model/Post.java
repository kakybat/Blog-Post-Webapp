package com.kakybat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Post title must not be blank")
    @Size(min = 10, message = "Post title must be at least 10 characters long")
    private String title;

    @NotBlank(message = "Short description must not be blank")
    @Size(min = 10, max = 100, message = "Short description should be between 20 and 100 characters")
    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "Post content must not be blank")
    @Size(min = 10, message = "Post content must be at least 200 characters long")
    private String body;

    @NotBlank(message = "Post header image required")
    private String imageFilePath;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    //name = "account_id", referencedColumnName = "id", nullable = false
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "comment_id", referencedColumnName = "id", nullable = false)
    private List<Comment> comments;

    public Post(){
        //
    }

    public Post(Long id, String title, String shortDescription, String body, LocalDate createdAt, LocalDate updatedAt, String imageFilePath, User user, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.shortDescription = shortDescription;
        this.body = body;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.imageFilePath = imageFilePath;
        this.user = user;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Post title must not be blank") @Size(min = 10, message = "Post title must be at least 10 characters long") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "Post title must not be blank") @Size(min = 10, message = "Post title must be at least 10 characters long") String title) {
        this.title = title;
    }

    public @NotBlank(message = "Short description must not be blank") @Size(min = 10, max = 100, message = "Short description should be between 20 and 100 characters") String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(@NotBlank(message = "Short description must not be blank") @Size(min = 10, max = 100, message = "Short description should be between 20 and 100 characters") String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public @NotBlank(message = "Post content must not be blank") @Size(min = 10, message = "Post content must be at least 200 characters long") String getBody() {
        return body;
    }

    public void setBody(@NotBlank(message = "Post content must not be blank") @Size(min = 10, message = "Post content must be at least 200 characters long") String body) {
        this.body = body;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
                "id=" + id +
                ", title='" + title + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", body='" + body + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", imageFilePath=" + imageFilePath +
                ", user=" + user +
                ", comments=" + comments +
                '}';
    }
}
