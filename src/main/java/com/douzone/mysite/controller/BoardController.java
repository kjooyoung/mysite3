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
	
	@RequestMapping({"/{page}","/{page}/{str}"})
	public String list(
			@PathVariable("page") Integer page, 
			@RequestParam(value="kwd", required=false, defaultValue="") String kwd,
			@PathVariable("str") Optional<String> str, Model model){
		if(str.isPresent()) kwd = str.get();
		Map<String, Object> map = boardService.list(page, kwd);
		model.addAttribute("map", map);
		return "board/list";
	}
	
	@RequestMapping("/view/{page}/{no}")
	public String view(@PathVariable("page") Integer page,
					@PathVariable("no") Long no, Model model, HttpSession session) {
		Map<String, Object> map = boardService.view(no);
		model.addAttribute("map", map);
		model.addAttribute("page", page);
		return "board/view";
	}
	
	@RequestMapping(value= {"/write","/write/{boardNo}"}, method=RequestMethod.GET)
	public String write(@PathVariable("boardNo") Optional<Long> boardNo, Model model ) {
		if(boardNo.isPresent()) {
			model.addAttribute("boardNo",boardNo.get());
		}else {
			model.addAttribute("boardNo",0);
		}
		return "board/write";
	}
	
	@RequestMapping(value= {"/write"}, method=RequestMethod.POST)
	public String write(@RequestParam(value="boardNo", required=false, defaultValue="0") Long boardNo,
						@ModelAttribute BoardVo boardVo, HttpSession session ) {
		System.out.println(boardNo);
		UserVo vo = (UserVo)session.getAttribute("authuser");
		boardVo.setUserNo(vo.getNo());
		boardVo.setNo(boardNo);
		boardService.write(boardVo);
		return "redirect:/board/1";
	}
	
	@RequestMapping("/delete/{no}")
	public String delete(@PathVariable("no") Long no) {
		boardService.delete(no);
		return "redirect:/board/1";
	}
	
	@RequestMapping(value="/modify/{page}/{no}", method=RequestMethod.GET)
	public String modify(@PathVariable("no") Long no, @PathVariable("page") String page, Model model) {
		model.addAttribute("board", boardService.getBoard(no));
		model.addAttribute("page",page);
		
		return "board/modify";
	}
	
	@RequestMapping(value="/modify/{page}/{no}", method=RequestMethod.POST)
	public String modify(@PathVariable("no") Long no, @PathVariable("page") Long page, 
			@ModelAttribute BoardVo boardVo) {
		boardService.update(boardVo);
		return "redirect:/board/view/"+page+"/"+no;
	}
	
}
