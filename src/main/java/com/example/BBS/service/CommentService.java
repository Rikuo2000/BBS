package com.example.BBS.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;

import com.example.BBS.model.Comment;
import com.example.BBS.model.User;
import com.example.BBS.repository.CommentRepository;

@Service
public class CommentService {

    private final SecurityFilterChain securityFilterChain;
	private final  CommentRepository commentRepository;
	
	public CommentService(CommentRepository commentRepository, SecurityFilterChain securityFilterChain) {
		this.commentRepository = commentRepository;
		this.securityFilterChain = securityFilterChain;
	}
	
	public Optional<Comment> findById(Long id) {
		return commentRepository.findById(id);
	}
	
	public List<Comment> findByPostId(Long postid) {
		return commentRepository.findByPostId(postid);
	}
	
	public Comment save(Comment comment) {
		return commentRepository.save(comment);
	}
	
	public void deleteById(Long id) {
		commentRepository.deleteById(id);
	}
	
	public boolean verifyOwnership(Comment comment, User user) {
		if (comment.getUser() == null) {
			return false;
		}
		if (!comment.getUser().getId().equals(user.getId())) {
			return false;
		}
		return true;
	}

}
