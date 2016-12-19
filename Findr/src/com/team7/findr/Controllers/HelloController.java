package com.team7.findr.Controllers;


import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.team7.findr.user.User;

@Controller
@RequestMapping("/login")
public class HelloController {

	@RequestMapping(method = RequestMethod.GET)
	public String goLogin(ModelMap model) {
		model.addAttribute("message", "Hello Spring MVC Framework!");
		return "login";
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public void redirect(@RequestBody User user, HttpServletRequest request) {
		// TODO: if we have time we can do some password authntication
		request.getSession().setAttribute("email", user.getEmail());
	}
}
