package com.bme.receiptrecognizer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController {

	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String error(ModelMap model) {
		model.addAttribute("error", "true");
		return "login";

	}
	
	@RequestMapping(value = "/error404", method = RequestMethod.GET)
	public String error404() {
		return "error404";
	}


	@RequestMapping(value= "/login", method = RequestMethod.GET)
	public String login() {
		System.out.println("Inside login");
		return "login";
	}

	@RequestMapping(value= "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "logout";
	}

}
