package com.example.BBS.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.BBS.model.Like;
import com.example.BBS.model.Post;
import com.example.BBS.model.User;
import com.example.BBS.repository.LikeRepository;

@Service
public class LikeService {
	private final LikeRepository likeRepository;
	
	public LikeService(LikeRepository likeRepository) {
		this.likeRepository = likeRepository;
	}
	
	public void toggleLike(User user, Post post) {
		Optional<Like> existingLike = likeRepository.findByUserAndPost(user, post);
		
		if (existingLike.isPresent()) {
			likeRepository.delete(existingLike.get());
		} else {
			Like like = new Like();
			like.setUser(user);
			like.setPost(post);
			likeRepository.save(like);
		}
	}
	
	public boolean isLikedByUser(Post post, User user) {
		return likeRepository.existsByPostAndUser(post, user);
	}
	
	public int countLikesForPost(Post post) {
		return likeRepository.countByPost(post);
	}
}
