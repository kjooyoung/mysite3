package com.douzone.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.douzone.mysite.service.ReplyService;
import com.douzone.mysite.vo.ReplyVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/reply")
public class ReplyController {
	@Autowired
	private ReplyService replyService;
	
	@RequestMapping("/write/{boardNo}")
	public String write(@RequestParam(value="page", required=false, defaultValue="1") Integer page,
			@PathVariable("boardNo") Long boardNo, 
			HttpSession session, @ModelAttribute ReplyVo replyVo) {
		UserVo vo = (UserVo)session.getAttribute("authuser");
		replyVo.setBoardNo(boardNo);
		replyVo.setUserNo(vo.getNo());
		replyService.write(replyVo);
		return "redirect:/board/view/"+boardNo+"?page="+page;
	}
	
	@RequestMapping("/delete/{boardNo}/{replyNo}")
	public String delete(@RequestParam(value="page", required=false, defaultValue="1") Integer page,
						@PathVariable("boardNo") Long boardNo, 
						@PathVariable("replyNo") Long replyNo) {
		replyService.delete(replyNo);
		return "redirect:/board/view/"+boardNo+"?page="+page;
	}
	
	@RequestMapping(value="/update/{boardNo}/{replyNo}")
	public String update(@RequestParam(value="page", required=false, defaultValue="1") Integer page,
						@PathVariable("boardNo") Long boardNo, 
						@PathVariable("replyNo") Long replyNo,
						@ModelAttribute ReplyVo replyVo) {
		replyVo.setNo(replyNo);
		replyService.update(replyVo);
		return "redirect:/board/view/"+boardNo+"?page="+page;
	}
	
}
