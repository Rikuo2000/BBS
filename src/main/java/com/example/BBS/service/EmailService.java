package com.example.BBS.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	private JavaMailSender mailSender;

	@Value("${mail.from}")
	private String fromAddress;

	@Value("${mail.admin}")
	private String adminAddress;

	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendUserEmail(String to, String subject, String text) {
		sendEmail(to, subject, text);
	}

	public void sendAdminEmail(String subject, String text) {
		sendEmail(adminAddress, subject, text);
	}

	public void sendEmail(String to, String subject, String text) {
		var message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		message.setFrom(fromAddress);
		mailSender.send(message);
	}

}
