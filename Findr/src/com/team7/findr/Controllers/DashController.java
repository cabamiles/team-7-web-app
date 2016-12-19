package com.team7.findr.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashController {

	@RequestMapping("/main")
	public ModelAndView mainView() {
		return new ModelAndView("main");
	}
}
