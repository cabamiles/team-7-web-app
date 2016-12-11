package com.team7.findr.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

	@RequestMapping("/test")
	public ModelAndView printTest() {
		String test = "testing 1... 2... 3...";
		ModelAndView mv = new ModelAndView("test");
		mv.addObject("test", test);
		return mv;
	}
}
