package com.kakybat.service;

import com.kakybat.model.Person;
import com.kakybat.model.Post;
import com.kakybat.repository.PostRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public Optional<Post> getById(Long id){
        return postRepository.findById(id);
    }

    public Post findById(Long postId){
        return postRepository.findById(postId).orElse(null);
    }


    public List<Post> getAll(){
        return postRepository.findAll();
    }
    public List<Post> getMyPosts(Person person){
        return postRepository.findByPerson(person);
    }
    public void save(Post post){
        if(post.getId() == null){
            post.setCreatedAt(LocalDateTime.now());
        }
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.save(post);
    }

    public void delete(Post post){
        postRepository.delete(post);
    }

}