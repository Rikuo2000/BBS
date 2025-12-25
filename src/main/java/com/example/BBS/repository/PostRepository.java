package com.example.BBS.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BBS.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	//部分一致
	Page<Post> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword, Pageable pageable);
	
	//前方一致
	Page<Post> findByTitleStartingWithOrContentStartingWith(String titleKeyword, String contentKeyword, Pageable pageable);
	
	//後方一致
	Page<Post> findByTitleEndingWithOrContentEndingWith(String titleKeyword, String contentKeyword, Pageable pageable);
}
