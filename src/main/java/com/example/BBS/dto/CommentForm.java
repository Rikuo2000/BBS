package com.example.BBS.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentForm {
	@NotBlank(message = "{error.content.content.blunk}")
	@Size(max = 1000, message = "{error.content.content.size}")
	private String content;

}
