package com.gj.test.index.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gj.test.base.controller.BaseController;

@Controller
public class IndexController  extends BaseController {
	
/*	@RequestMapping("/login")
	public String login() {
		return "index/login";
	}*/
	
	@RequestMapping("/loginSuccess")
	public String loginSuccess() {
		return "index/login";
	}

}
