package com.ankit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ankit.exception.ResourceNotFoundException;
import com.ankit.model.Post;
import com.ankit.model.User;
import com.ankit.repository.PostRepository;
import com.ankit.service.UserService;

@RestController
@RequestMapping("users/{userId}/posts")
public class PostsController {

	@Autowired
	UserService userService;

	@Autowired
	PostRepository postRepository;

	@GetMapping()
	public List<Post> getAllPostsForUser(@PathVariable Long userId) {
		return postRepository.getPostsByUserId(userId);
	}

	@GetMapping("/{postId}")
	public Post getPostByIdForUserId(@PathVariable Long userId, @PathVariable Long postId) {
		return postRepository.getPostsByUserId(userId).stream().filter(p -> p.getId().equals(postId)).findFirst().get();
	}

	@PostMapping()
	public Post createPostForUserId(@PathVariable Long userId, @RequestBody Post post) {

		User user = userService.getUserById(userId);
		Post post1 = null;
		if (user != null) {
			post.setUser(user);
			post1 = postRepository.save(post);
		} else {
			throw new ResourceNotFoundException("User not found with id " + userId);
		}
		return post1;
	}
}
