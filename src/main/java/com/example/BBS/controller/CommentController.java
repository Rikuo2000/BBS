package com.example.BBS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.BBS.model.Comment;
import com.example.BBS.model.Post;
import com.example.BBS.model.User;
import com.example.BBS.service.CommentService;
import com.example.BBS.service.PostService;
import com.example.BBS.service.UserService;

@Controller
@RequestMapping("/comments")
public class CommentController {
	private final CommentService commentService;
	private final PostService postService;
	private final UserService userService;
	
	public CommentController(CommentService commentService, PostService postService, UserService userService) {
		this.commentService	= commentService;
		this.postService 	= postService;
		this.userService	= userService;
	}
	
	//投稿
	@PostMapping("/add")
	public String addComment(@RequestParam Long postId, @RequestParam String content) {
		Post post = postService.findById(postId).orElseThrow();
		User user = userService.getCurrentUser();
		
		Comment comment = new Comment();
		comment.setPost(post);
		comment.setContent(content);
		comment.setUser(user);
		
		commentService.save(comment);
		return "redirect:/posts/" + postId;
	}
	
	//削除
	@PostMapping("/{id}/delete")
	public String deleteComment(@RequestParam Long id, @RequestParam Long postId) {
		Comment comment = commentService.findById(id).orElseThrow(
				() -> new RuntimeException("Comment not forund"));
		
		commentService.deleteById(id);
		return "redirect:/posts/" + postId;
	}
	
}
