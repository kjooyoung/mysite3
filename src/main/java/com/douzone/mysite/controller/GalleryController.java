package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.service.FileuploadService;
import com.douzone.mysite.service.GalleryService;
import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.GalleryVo;
import com.douzone.security.Auth;
import com.douzone.security.Auth.Role;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
	@Autowired
	private GalleryService galleryService;
	@Autowired
	private FileuploadService fileuploadService;
	
	@RequestMapping("")
	public String index(Model model) {
		model.addAttribute("list",galleryService.getList());
		return "gallery/index";
	}
	
	@Auth(Role.ADMIN)
	@RequestMapping("/upload")
	public String upload(@ModelAttribute GalleryVo galleryVo,
						@RequestParam("upload-image") MultipartFile multipartFile) {
		galleryVo.setImageURL(fileuploadService.restore(multipartFile));
		galleryService.insert(galleryVo);
		return "redirect:/gallery";
	}
	
	@RequestMapping("/delete/{no}")
	public String delete(@PathVariable("no") Long no) {
		galleryService.delete(no);
		return "redirect:/gallery";
	}
	
	@Transactional
	@RequestMapping("/setProfile/{no}")
	public String setProfile(@PathVariable("no") Long no) {
		galleryService.updateProfile(no);
		return "redirect:/";
	}
}
