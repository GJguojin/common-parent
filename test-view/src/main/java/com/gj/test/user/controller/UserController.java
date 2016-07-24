package com.gj.test.user.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gj.test.base.controller.BaseController;
import com.gj.test.user.beans.User;
import com.gj.test.user.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	public static Log log = LogFactory.getLog(UserController.class);
	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	public String getUser(Model model) {
		User user = userService.selectById((long)1);
		model.addAttribute( "user", user );
		return "user/add";
	}
}
