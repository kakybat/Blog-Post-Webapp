package com.kakybat.service;

import com.kakybat.constants.AppConstants;
import com.kakybat.model.Person;
import com.kakybat.model.Post;
import com.kakybat.repository.PostRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
            post.setPostStatus(AppConstants.POST_STATUS_INACTIVE);
            post.setCreatedAt(LocalDateTime.now());
        }
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.save(post);
    }

    public List<Post> findPostsWithInactiveStatus(){
        return postRepository.findByPostStatus(AppConstants.POST_STATUS_INACTIVE);
    }

    public List<Post> getPostsWithActiveStatus(){
        return postRepository.findByPostStatus(AppConstants.POST_STATUS_ACTIVE);
    }

    public Page<Post> getPostsWithActiveStatus(int pageNumber, String sortField, String sortDir){
        int pageSize = 3;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending() :Sort.by(sortField).descending());

        return postRepository.findByPostStatus(AppConstants.POST_STATUS_ACTIVE, pageable);
    }

    public List<Post> getPostsWithNullStatus(){
        String NULLSTATUS = null;
        return postRepository.findByPostStatus(NULLSTATUS);
    }
    public void updatePostStatus(Long postId){
        Optional<Post> post = postRepository.findById(postId);
        post.ifPresent(post1 -> post1.setPostStatus(AppConstants.POST_STATUS_ACTIVE));
        postRepository.save(post.get());

    }

    public void delete(Post post){
        postRepository.delete(post);
    }

}