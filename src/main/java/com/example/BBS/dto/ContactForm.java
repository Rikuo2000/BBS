package com.example.BBS.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactForm {

	@NotBlank(message = "{error.contact.name.blank}")
	private String name;

	@NotBlank(message = "{error.contact.email.blank}")
	@Email(message = "{error.contact.email.invalid}")
	private String email;

	@NotBlank(message = "{error.contact.message.blank}")
	private String message;
}