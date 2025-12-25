package com.example.BBS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BBS.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	//部分一致
	List<Post> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);
	
	//前方一致
	List<Post> findByTitleStartingWithOrContentStartingWith(String titleKeyword, String contentKeyword);
	
	//後方一致
	List<Post> findByTitleEndingWithOrContentEndingWith(String titleKeyword, String contentKeyword);
}
