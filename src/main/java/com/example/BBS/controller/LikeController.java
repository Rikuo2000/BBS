package com.example.BBS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.BBS.model.Post;
import com.example.BBS.model.User;
import com.example.BBS.service.CommentService;
import com.example.BBS.service.LikeService;
import com.example.BBS.service.PostService;
import com.example.BBS.service.UserService;

@Controller
@RequestMapping("/posts")
public class LikeController {
	private final CommentService commentService;
	private final LikeService likeService;
	private final PostService postService;
	private final UserService userService;
	
	public LikeController(CommentService commentService, LikeService likeService, PostService postService, UserService userService) {
		this.commentService = commentService;
		this.likeService 	= likeService;
		this.postService 	= postService;
		this.userService 	= userService;
	}

	@PostMapping("/{postId}/like")
	public String toggleLike(@PathVariable Long postId) {
		User loggedInUser = userService.getCurrentUser();
		
		Post post = postService.findById(postId).orElseThrow();
		
		likeService.toggleLike(loggedInUser, post);
		
		return "redirect:/posts/" + postId;
	}
	
}
