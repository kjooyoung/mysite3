package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping("")
	public String list(Model model) {
		model.addAttribute("list",guestbookService.getList());
		return "guestbook/list";
	}
	
	@RequestMapping("add")
	public String list(@ModelAttribute GuestbookVo guestbookVo) {
		guestbookService.add(guestbookVo);
		return "redirect:/guestbook";
	}
	
	@RequestMapping(value="delete/{no}", method=RequestMethod.GET)
	public String delete(@PathVariable("no") Long no, Model model) {
		model.addAttribute("no", no);
		return "guestbook/delete";
	}
	
	@RequestMapping(value="delete", method=RequestMethod.POST)
	public String delete(@ModelAttribute GuestbookVo vo, Model model) {
		guestbookService.delete(vo);
		return "redirect:/guestbook";
	}
}
