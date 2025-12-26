package com.example.BBS.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.BBS.model.Like;
import com.example.BBS.model.Post;
import com.example.BBS.model.User;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long>{
	Optional<Like>findByUserAndPost(User user, Post post);
	boolean existsByPostAndUser(Post post, User user);
	int countByPost(Post post);

}
