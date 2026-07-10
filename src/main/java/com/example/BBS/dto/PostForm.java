package com.example.BBS.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostForm {

	@NotBlank(message = "入力は必須です")
	@Size(max = 100, message = "文字数は100文字までにしてください")
	private String title;

	@NotBlank(message = "入力は必須です")
	@Size(max = 1000, message = "文字数は1000文字までにしてください")
	private String content;

}
