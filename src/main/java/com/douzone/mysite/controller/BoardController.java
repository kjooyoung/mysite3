package com.douzone.mysite.controller;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="/{page}")
	public String list(
			@PathVariable("page") Integer page, 
			@RequestParam(value="kwd", required=false, defaultValue="") String kwd, Model model){
		Map<String, Object> map = boardService.list(page, kwd);
		model.addAttribute("map", map);
		return "board/list";
	}
	
	@RequestMapping("/view/{no}")
	public String view(@PathVariable("no") Long no, Model model) {
		Map<String, Object> map = boardService.get(no);
		model.addAttribute("map", map);
		return "board/view";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write() {
		return "board/write";
	}
	
	@RequestMapping(value= {"/write", "/write/{no}"}, method=RequestMethod.POST)
	public String write(@PathVariable Map<String, String> map,
			@ModelAttribute BoardVo boardVo, HttpSession session) {
		UserVo vo = (UserVo)session.getAttribute("authuser");
		boardVo.setUserNo(vo.getNo());
		System.out.println(map);
		if(map.get("no") == null) {
			map.put("no","0");
		}
		boardService.write(Long.parseLong(map.get("no")), boardVo);
		return "redirect:/board/1";
	}
	
	
}
