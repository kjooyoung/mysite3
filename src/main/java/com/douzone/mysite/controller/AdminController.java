package com.douzone.mysite.controller;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;
import com.douzone.security.Auth;
import com.douzone.security.Auth.Role;

@Controller
@Auth(Role.ADMIN)
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private SiteService siteService;
	
	@RequestMapping({"","/main"})
	public String main(Model model) {
		model.addAttribute("siteVo",siteService.get());
		return "admin/main";
	}
	
	@RequestMapping("/main/modify")
	public String modify(HttpSession session, @ModelAttribute SiteVo siteVo) {
		siteService.modify(siteVo);
		session.setAttribute("siteVo", siteVo);
		return "redirect:/";
	}
	
	@RequestMapping("/board")
	public String board() {
		
		return "admin/board";
	}
}
