package com.kakybat.repository;

import com.kakybat.model.Person;
import com.kakybat.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByPerson(Person person);
    List<Post> findByPostStatus(String postStatus);
    Page<Post> findByPostStatus(String postStatus, Pageable pageable);
}
