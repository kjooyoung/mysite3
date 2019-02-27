package com.douzone.mysite.controller;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.service.FileuploadService;
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
	@Autowired
	private FileuploadService fileuploadService;
	
	@RequestMapping({"","/main"})
	public String main(Model model) {
		model.addAttribute("siteVo",siteService.getSite());
		return "admin/main";
	}
	
	@RequestMapping("/main/modify")
	public String modifySite(HttpSession session, @ModelAttribute SiteVo siteVo,
						@RequestParam("upload-profile") MultipartFile multipartFile) {
		siteVo.setProfile(fileuploadService.restore(multipartFile));
		siteService.modifySite(siteVo);
		session.setAttribute("siteVo", siteVo);
		
		return "redirect:/admin/main";
	}
	
	@RequestMapping("/board")
	public String board() {
		
		return "admin/board";
	}
}
