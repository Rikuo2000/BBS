package com.example.BBS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.BBS.model.Post;
import com.example.BBS.service.PostService;

@Controller
@RequestMapping("/posts")
public class PostController {
	private final PostService postService;
	
	public PostController(PostService postService) {
		this.postService = postService;
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