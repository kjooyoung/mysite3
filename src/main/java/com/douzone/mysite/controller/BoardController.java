package com.douzone.mysite.controller;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.security.Auth;
import com.douzone.security.AuthUser;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping({"","/list"})
	public String list(
			@RequestParam(value="page", required=false, defaultValue="1") Integer page, 
			@RequestParam(value="kwd", required=false, defaultValue="") String kwd,
			@PathVariable("str") Optional<String> str, Model model){
		if(str.isPresent()) kwd = str.get();
		Map<String, Object> map = boardService.list(page, kwd);
		model.addAttribute("map", map);
		return "board/list";
	}
	
	@RequestMapping("/view/{no}")
	public String view(@RequestParam(value="page", required=false, defaultValue="1") Integer page,
					@RequestParam(value="kwd", required=false, defaultValue="") String kwd,
					@PathVariable("no") Long no, Model model) {
		Map<String, Object> map = boardService.view(no);
		model.addAttribute("map", map);
		model.addAttribute("page", page);
		model.addAttribute("kwd",kwd);
		return "board/view";
	}
	
	@Auth
	@RequestMapping(value= {"/write","/write/{boardNo}"}, method=RequestMethod.GET)
	public String write(@RequestParam(value="page", required=false, defaultValue="1") Integer page,
			@RequestParam(value="kwd", required=false, defaultValue="") String kwd,
			@PathVariable("boardNo") Optional<Long> boardNo, Model model ) {
		if(boardNo.isPresent()) {
			model.addAttribute("boardNo",boardNo.get());
			
		}else {
			model.addAttribute("boardNo",0);
		}
		model.addAttribute("page", page);
		model.addAttribute("kwd",kwd);
		return "board/write";
	}
	
	@Auth
	@Transactional
	@RequestMapping(value= {"/write"}, method=RequestMethod.POST)
	public String write(@RequestParam(value="boardNo", required=false, defaultValue="0") Long boardNo,
			@RequestParam(value="page", required=false, defaultValue="1") Integer page,
			@RequestParam(value="kwd", required=false, defaultValue="") String kwd,
			@ModelAttribute BoardVo boardVo, @AuthUser UserVo authUser, Model model ) {
		
		boardVo.setUserNo(authUser.getNo());
		boardVo.setNo(boardNo);
		boardService.write(boardVo);
		model.addAttribute("page",page);
		return "redirect:/board/view/"+boardVo.getNo();
	}
	
	@Auth
	@RequestMapping("/delete/{no}")
	public String delete(@PathVariable("no") Long no, @AuthUser UserVo authUser) {
		//세션유저 no 와 글 작성자 no가 같아야 삭제할 수있게 해야함
		if(authUser.getNo() == no) {
			boardService.delete(no);
		}
		return "redirect:/board";
	}
	
	@Auth
	@RequestMapping(value="/modify/{no}", method=RequestMethod.GET)
	public String modify(@RequestParam(value="page", required=false, defaultValue="1") Integer page,
					@PathVariable("no") Long no, Model model, @AuthUser UserVo authUser) {
		model.addAttribute("board", boardService.getBoard(no));
		model.addAttribute("page",page);
		return "board/modify";
	}
	
	@Auth
	@RequestMapping(value="/modify/{no}", method=RequestMethod.POST)
	public String modify(@RequestParam(value="page", required=false, defaultValue="1") Integer page,
			@PathVariable("no") Long no, @ModelAttribute BoardVo boardVo) {
		boardService.update(boardVo);
		return "redirect:/board/view/"+no+"?page="+page;
	}
	
}
