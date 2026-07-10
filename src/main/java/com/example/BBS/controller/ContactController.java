package com.example.BBS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.BBS.model.ContactData;
import com.example.BBS.service.EmailService;

@Controller
@RequestMapping("/contact")
@SessionAttributes("contactData")
public class ContactController {

	private EmailService emailService;

	public ContactController(EmailService emailService) {
		this.emailService = emailService;
	}

	@GetMapping
	public String contctForm(Model model) {
		if (!model.containsAttribute("contactData")) {
			model.addAttribute("contactData", new ContactData());
		}
		return "contact/form";
	}

	@PostMapping("/confirm")
	public String confirmContact(
			@RequestParam("name") String name,
			@RequestParam("email") String email,
			@RequestParam("message") String message,
			Model model) {
		model.addAttribute("contactData", new ContactData(name, email, message));
		return "contact/confirm";
	}

	@PostMapping("/submit")
	public String submitContact(
			@RequestParam("name") String name,
			@RequestParam("email") String email,
			@RequestParam("message") String message) {

		//ユーザに送信するメール内容
		String userSubject = "お問い合わせありがとうございます。";
		String userBody = String.format(
				"お問い合わせありがとうございます。\n以下の内容で受け付けました。\n\n[お問い合わせ内容」\n%s", name, message);

		//運営者に送信するメール内容
		String adminSubject = "新しいお問い合わせが届きました。";
		String adminBody = String.format(
				"新しいお問い合わせが届きました。\n\n[お名前]: %s\n[メールアドレス]: %s\n[お問い合わせ内容]: \n%s", name, email, message);

		emailService.sendUserEmail(email, userSubject, userBody);

		emailService.sendAdminEmail(adminSubject, adminBody);

		return "redirect:/contact/complete";
	}

	@GetMapping("/complete")
	public String completeForm() {
		return "contact/complete";
	}

}
