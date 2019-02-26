package com.douzone.mysite.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.dto.JSONResult;
import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;

@Controller("guestbookApiController")
@RequestMapping("/guestbook/api")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping("")
	public String main() {
		return "guestbook/index-ajax";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public JSONResult list(
			@RequestParam(value="page", required=false, defaultValue="1") Integer page) {
		
		return JSONResult.success(guestbookService.getListByPage(page));
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public JSONResult add(@ModelAttribute GuestbookVo guestbookVo) {
		guestbookVo.setNo(guestbookService.add(guestbookVo));
		return JSONResult.success(guestbookVo);
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public JSONResult delete(@ModelAttribute GuestbookVo guestbookVo) {
		return JSONResult.success(guestbookService.delete(guestbookVo));
	}
}
