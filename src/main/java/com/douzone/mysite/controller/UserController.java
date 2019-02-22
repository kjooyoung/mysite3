package com.douzone.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() {
		return "user/join";
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@ModelAttribute UserVo userVo) {
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}

	@RequestMapping("/joinsuccess")
	public String joinSuccess() {
		return "/user/joinsuccess";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "/user/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute UserVo userVo, Model model, HttpSession session) {
		UserVo authUser = userService.login(userVo);
		if (authUser == null) {
			model.addAttribute("result", "fail");
			return "/user/login";
		}
		session.setAttribute("authuser", authUser);
		System.out.println(authUser.getRole());
		return "redirect:/";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		if (session != null && session.getAttribute("authuser") != null) {
			// logout처리
			session.removeAttribute("authuser");
			// browser 메모리에 있는 session id 날림
			session.invalidate();
		}
		return "redirect:/";
	}

	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modify(Model model, HttpSession session) {
		UserVo authUser = null;
		
		if(session != null) {
			authUser = (UserVo)session.getAttribute("authuser");
		}
		if(authUser == null) {
			return "redirect:/";
		}
		return "user/modify";
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(@ModelAttribute UserVo userVo, HttpSession session) {
		UserVo authUser = (UserVo) session.getAttribute("authuser");
		userVo.setNo(authUser.getNo());
		userService.modify(userVo);
		session.setAttribute("authuser", userService.getUser(userVo.getNo()));
		return "redirect:/";
	}
}
