package com.douzone.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.dto.JSONResult;
import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.UserVo;

@Controller
public class MainController {
	@Autowired
	private SiteService siteService;
	
	@RequestMapping({"", "/main"})
	public String main(HttpSession session, Model model) {
		session.setAttribute("siteVo", siteService.get());
		return "main/index";
	}
	
	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		return "<h1>안녕하세여</h1>";
	}
	
	@ResponseBody
	@RequestMapping("/hello2")
	public JSONResult hello2() {
		return JSONResult.success(new UserVo());
	}
}
