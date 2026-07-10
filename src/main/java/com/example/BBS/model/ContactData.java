package com.example.BBS.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactData {
	private String name, email, message;
	
	public ContactData() {	
	}
	
	public ContactData(String name, String email, String message) {
		this.name = name;
		this.email = email;
		this.message = message;
	}

}
