package com.example.BBS.controller;

import jakarta.validation.Valid;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.BBS.dto.CommentForm;
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
	private final MessageSource messageSource;

	public CommentController(CommentService commentService, PostService postService, UserService userService,
			MessageSource messageSource) {
		this.commentService = commentService;
		this.postService = postService;
		this.userService = userService;
		this.messageSource = messageSource;
	}

	//投稿
	@PostMapping("/add")
	public String addComment(@RequestParam Long postId, @Valid @ModelAttribute("commentForm") CommentForm commentForm,
			BindingResult result, RedirectAttributes redirectAttributes) {
		//
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.commentForm", result);
			redirectAttributes.addFlashAttribute("commentForm", commentForm);
			//フラッシュメッセージをセット
			redirectAttributes.addFlashAttribute("errorMessage",
					messageSource.getMessage("comment.create.failed", null, LocaleContextHolder.getLocale()));
			return "redirect:/posts/" + postId;
		}

		Post post = postService.findById(postId).orElseThrow();
		User user = userService.getCurrentUser();

		Comment comment = new Comment();
		comment.setPost(post);
		comment.setUser(user);
		comment.setContent(commentForm.getContent());

		commentService.save(comment);
		//フラッシュメッセージをセット
		redirectAttributes.addFlashAttribute("successMessage",
				messageSource.getMessage("comment.create.success", null, LocaleContextHolder.getLocale()));
		return "redirect:/posts/" + postId;
	}

	//削除
	@PostMapping("/{id}/delete")
	public String deleteComment(@PathVariable Long id, @RequestParam Long postId,
			RedirectAttributes redirectAttributes) {
		//ログインユーザー取得
		User loggedInUser = userService.getCurrentUser();
		Comment comment = commentService.findById(id).orElseThrow(
				() -> new RuntimeException("Comment not found"));
		//投稿の所有者確認
		if (!commentService.verifyOwnership(comment, loggedInUser)) {
			//フラッシュメッセージをセット
			redirectAttributes.addFlashAttribute("errorMessage",
					messageSource.getMessage("comment.delete.failed", null, LocaleContextHolder.getLocale()));
			return "redirect:/posts/" + postId + "?error=notAuthorized";
		}
		commentService.deleteById(id);
		//フラッシュメッセージをセット
		redirectAttributes.addFlashAttribute("successMessage",
				messageSource.getMessage("comment.delete.success", null, LocaleContextHolder.getLocale()));
		return "redirect:/posts/" + postId;
	}

}
