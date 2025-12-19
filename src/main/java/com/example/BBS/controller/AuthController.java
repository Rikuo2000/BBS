package com.example.BBS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.BBS.model.User;
import com.example.BBS.service.CoustomUserDitailsService;

@Controller
@RequestMapping("/auth")
public class AuthController {

	private final CoustomUserDitailsService userDetailsService;
	
	public AuthController(CoustomUserDitailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	@GetMapping("/login")
	public String login() {
		return "auth/login";
	}
	
	@GetMapping("/register")
	public String registerForm(Model model) {
		model.addAttribute("user", new User());
		return "auth/register";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute User user, RedirectAttributes ra) {
        try {
            userDetailsService.registerUser(user);
            ra.addFlashAttribute("registered", true);
            return "redirect:/auth/login?registered";
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
            return "redirect:/auth/register";
        } catch (Exception ex) {
            ra.addFlashAttribute("error", "登録に失敗しました");
            return "redirect:/auth/register";
        }
	}
}
