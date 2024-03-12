package com.kakybat.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String shortDescription;
    @Column(columnDefinition = "TEXT")
    private String body;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String imageFilePath;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    @JsonFormat(pattern = "dd/MM/yyyy")
    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @JsonFormat(pattern = "dd/MM/yyyy")
    public LocalDate getUpdatedAt(){
        return updatedAt;
    }
    public void setUpdatedAt(LocalDate updatedAt){
        this.updatedAt = updatedAt;
    }
    public String getImageFilePath(){
        return imageFilePath;
    }
    public void setImageFilePath(String imageFilePath){
        this.imageFilePath = imageFilePath;
    }
    public User getUser(){
        return user;
    }
    public void setUser(User user){
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
