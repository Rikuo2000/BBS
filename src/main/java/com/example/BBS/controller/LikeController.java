package com.example.BBS.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BBS.model.Post;
import com.example.BBS.model.User;
import com.example.BBS.service.CommentService;
import com.example.BBS.service.LikeService;
import com.example.BBS.service.PostService;
import com.example.BBS.service.UserService;

@RestController
@RequestMapping("/posts")
public class LikeController {
	private final CommentService commentService;
	private final LikeService likeService;
	private final PostService postService;
	private final UserService userService;

	public LikeController(CommentService commentService, LikeService likeService, PostService postService,
			UserService userService) {
		this.commentService = commentService;
		this.likeService = likeService;
		this.postService = postService;
		this.userService = userService;
	}

	@PostMapping("/{postId}/like")
	public Map<String, Object> toggleLike(@PathVariable Long postId) {
		Map<String, Object> response = new HashMap<>();

		User loggedInUser = userService.getCurrentUser();

		Post post = postService.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

		likeService.toggleLike(loggedInUser, post);

		Boolean isLiked = likeService.isLikedByUser(post, loggedInUser);

		int likeCount = likeService.countLikesForPost(post);

		response.put("isLiked", isLiked);
		response.put("likeCount", likeCount);

		return response;
	}

}
