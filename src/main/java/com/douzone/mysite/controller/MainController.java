package com.douzone.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.mysite.service.SiteService;

@Controller
public class MainController {
	@Autowired
	private SiteService siteService;
	@RequestMapping({"", "/main"})
	public String main(HttpSession session, Model model) {
		session.setAttribute("siteVo", siteService.get());
		return "main/index";
	}
}
