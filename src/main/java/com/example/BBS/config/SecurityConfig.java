package com.example.BBS.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/", "/auth/register", "/auth/login", "/contact/**")
				.permitAll().anyRequest().authenticated()
				).formLogin(form -> form
						.loginPage("/auth/login")
						.loginProcessingUrl("/auth/login")
						.defaultSuccessUrl("/posts", true)
						.permitAll()
						).logout(logout -> logout
								.logoutUrl("/auth/logout")
								.logoutSuccessUrl("/auth/login?logout")
								.invalidateHttpSession(true)
								.deleteCookies("JSESSIONID")
								.permitAll()
								);
		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
