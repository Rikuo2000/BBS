package com.example.BBS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BBS.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
