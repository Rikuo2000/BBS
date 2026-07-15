package com.example.BBS.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostForm {

	@NotBlank(message = "{error.post.title.blunk}")
	@Size(max = 100, message = "{error.post.title.size}")
	private String title;

	@NotBlank(message = "{error.post.content.blunk}")
	@Size(max = 1000, message = "{error.post.content.size}")
	private String content;

}
