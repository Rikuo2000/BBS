package com.example.BBS.controller;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.BBS.dto.CommentForm;
import com.example.BBS.dto.PostForm;
import com.example.BBS.model.Post;
import com.example.BBS.model.User;
import com.example.BBS.service.CommentService;
import com.example.BBS.service.LikeService;
import com.example.BBS.service.PostService;
import com.example.BBS.service.UserService;

@Controller
@RequestMapping("/posts")
public class PostController {
	private final CommentService commentService;
	private final LikeService likeService;
	private final PostService postService;
	private final UserService userService;

	public PostController(PostService postService, LikeService likeService, CommentService commentService,
			UserService userService) {
		this.commentService = commentService;
		this.likeService = likeService;
		this.postService = postService;
		this.userService = userService;
	}

	//投稿一覧表示
	@GetMapping
	public String listPosts(
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "matchType", required = false, defaultValue = "contains") String matchType,
			@RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
			@RequestParam(value = "sortOrder", required = false, defaultValue = "asc") String sortOrder,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			Model model) {
		Page<Post> posts;
		int size = 10; //表示サイズ
		if (keyword != null && !keyword.isEmpty() && matchType != null && !matchType.isEmpty()) {
			posts = postService.searchPosts(keyword, matchType, sortBy, sortOrder, page, size);
		} else {
			posts = postService.findAll(sortBy, sortOrder, page, size);
		}
		User loggedUser = userService.getCurrentUser();
		model.addAttribute("posts", posts);
		model.addAttribute("loggedInUserId", loggedUser.getId());

		return "posts/list";
	}

	//投稿の詳細表示
	@GetMapping("/{id}")
	public String viewPost(@PathVariable Long id, Model model) {
		User loggedUser = userService.getCurrentUser();
		Post post = postService.findById(id).orElseThrow();
		model.addAttribute("loggedInUserId", loggedUser.getId());
		model.addAttribute("post", post);
		model.addAttribute("comments", commentService.findByPostId(id));
		if (!model.containsAttribute("commentForm")) {
			model.addAttribute("commentForm", new CommentForm());
		}

		boolean isLiked = likeService.isLikedByUser(post, loggedUser);
		model.addAttribute("isLiked", isLiked);
		int likeCount = likeService.countLikesForPost(post);
		model.addAttribute("likeCount", likeCount);
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
	public String createPost(@Valid @ModelAttribute("post") PostForm postForm, BindingResult result, Model model) {
		//バリテーションエラーがある場合は、エラー情報を含めたフォーム画面へ戻す
		if (result.hasErrors()) {
			model.addAttribute("post", postForm);
			return "posts/new";
		}
		//ログイン中のユーザー情報取得
		User user = userService.getCurrentUser();

		//バリテーションが通ったら、DTOの内容をエンティティに変換
		var post = new Post();
		post.setTitle(postForm.getTitle());
		post.setContent(postForm.getContent());
		post.setUser(user);

		postService.save(post);
		return "redirect:/posts";
	}

	//編集画面
	@GetMapping("/{id}/edit")
	public String editPostForm(@PathVariable Long id, Model model) {
		User loggedUser = userService.getCurrentUser();
		Post post = postService.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
		if (!postService.verifyOwnership(post, loggedUser)) {
			return "redirect:/posts?error=notAuthorized";
		}
		model.addAttribute("post", post);
		return "posts/edit";
	}

	//投稿更新
	@PostMapping("/{id}")
	public String updatePost(@PathVariable Long id, @ModelAttribute Post post) {
		User loggedUser = userService.getCurrentUser();
		Post existingPost = postService.findById(id).orElseThrow();
		if (!postService.verifyOwnership(existingPost, loggedUser)) {
			return "redirect:/posts?error=notAuthorized";
		}
		existingPost.setTitle(post.getTitle());
		existingPost.setContent(post.getContent());
		postService.save(existingPost);
		return "redirect:/posts";
	}

	//投稿削除
	@PostMapping("/{id}/delete")
	public String deletePost(@PathVariable Long id) {
		User loggedUser = userService.getCurrentUser();
		Post post = postService.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
		if (!postService.verifyOwnership(post, loggedUser)) {
			return "redirect:/posts?error=notAuthorized";
		}
		postService.deleteById(id);
		return "redirect:/posts";
	}
}