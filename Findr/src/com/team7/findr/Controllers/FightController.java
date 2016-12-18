package com.team7.findr.Controllers;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/fight")
public class FightController {

	@RequestMapping(method = RequestMethod.GET)
	public String printHello(ModelMap model) {
		model.addAttribute("message", "Hello Spring MVC Framework!");
		return "fight";
	}
}
