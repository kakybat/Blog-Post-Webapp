package com.kakybat.serviceImpl;

import com.kakybat.model.Post;
import com.kakybat.model.User;
import com.kakybat.repository.PostRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public List<Post> getAll(){
        return postRepository.findAll();
    }
    public List<Post> getMyPosts(User user){
        return user.getPosts();

    }
    public void save(Post post){
        if(post.getId() == null){
            post.setCreatedAt(LocalDate.now());
        }
        post.setUpdatedAt(LocalDate.now());
        postRepository.save(post);
    }

    public void delete(Post post){
        postRepository.delete(post);
    }

}