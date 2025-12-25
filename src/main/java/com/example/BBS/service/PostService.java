package com.example.BBS.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.BBS.model.Post;
import com.example.BBS.model.User;
import com.example.BBS.repository.PostRepository;

@Service
public class PostService {

    private final PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	public Post save(Post post) {
		return postRepository.save(post);
	}
	
	public List<Post> findAll(String sortBy, String sortOrder) {
		Sort.Order order;
		if (sortOrder.equals("asc")){
			order = new Sort.Order(Sort.Direction.ASC, sortBy);
		}else {

			order = new Sort.Order(Sort.Direction.DESC, sortBy);
		}
		Sort sort = Sort.by(order);
		return postRepository.findAll(sort);
	}
	
	public List<Post> searchPosts(String keyword, String matchType, String sortBy, String sortOrder) {
		Sort.Order order;
		if (sortOrder.equals("asc")){
			order = new Sort.Order(Sort.Direction.ASC, sortBy);
		}else {

			order = new Sort.Order(Sort.Direction.DESC, sortBy);
		}

		Sort sort = Sort.by(order);
		
		
		switch (matchType) {
		case "startswith":
			return postRepository.findByTitleStartingWithOrContentStartingWith(keyword, keyword, sort);
		case "endswith":
			return postRepository.findByTitleEndingWithOrContentEndingWith(keyword, keyword, sort);
		case "contains":
			default:
				return postRepository.findByTitleContainingOrContentContaining(keyword, keyword, sort);
		}
	}
	
	public Optional<Post> findById(Long id) {
		return postRepository.findById(id);
	}
	
	//削除
	public void deleteById(Long id) {
		postRepository.deleteById(id);
	}
	
	public boolean verifyOwnership(Post post, User user) {
		if (post.getUser() == null) {
			return false;
		}
		if (!post.getUser().getId().equals(user.getId())) {
			return false;
		}
		return true;
	}
}
