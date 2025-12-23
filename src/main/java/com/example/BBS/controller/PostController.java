package com.example.BBS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.BBS.model.Post;
import com.example.BBS.model.User;
import com.example.BBS.service.CommentService;
import com.example.BBS.service.PostService;
import com.example.BBS.service.UserService;

@Controller
@RequestMapping("/posts")
public class PostController {
	private final CommentService commentService;
	private final PostService postService;
	private final UserService userService;
	
	public PostController(PostService postService, CommentService commentService, UserService userService) {
		this.commentService = commentService;
		this.postService 	= postService;
		this.userService	= userService;
	}
	
	@GetMapping
	public String listPosts(Model model) {
		model.addAttribute("posts", postService.findAll());
		return "posts/list";
	}
	
	//投稿の詳細表示
	@GetMapping("/{id}")
	public String viewPost(@PathVariable Long id, Model model){
		model.addAttribute("post", postService.findById(id).orElseThrow());
		return "posts/detail";
	}
	//新規投稿画面
	@GetMapping("/new")
	public String newPostForm(Model model) {
		model.addAttribute("post", new Post());
		return "posts/new";
	}
	
	
	//新規投稿
	@PostMapping
	public String createPost(@ModelAttribute Post post) {
		//ログイン中のユーザー情報取得
		User user = userService.getCurrentUser();
		post.setUser(user);
		postService.save(post);
		return "redirect:/posts";
	}
	
	//編集画面
	@GetMapping("/{id}/edit")
	public String editPostForm(@PathVariable Long id, Model model){
		model.addAttribute("post", postService.findById(id).orElseThrow());
		return "posts/edit";
	}
	
	//投稿更新
	@PostMapping("/{id}")
	public String updatePost(@PathVariable Long id, @ModelAttribute Post post) {
		Post existingPost = postService.findById(id).orElseThrow();
		existingPost.setTitle(post.getTitle());
		existingPost.setContent(post.getContent());
		postService.save(existingPost);
		return "redirect:/posts";
	}
	
	//投稿削除
	@PostMapping("{id}/delete")
	public String deletePost(@PathVariable Long id) {
		postService.deleteById(id);
		return "redirect:/posts";
	}
}