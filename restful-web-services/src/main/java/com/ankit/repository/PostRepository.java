package com.ankit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ankit.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> getPostsByUserId(Long id);

}
