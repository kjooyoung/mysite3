package com.douzone.mysite.exception;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.douzone.dto.JSONResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Log LOG = LogFactory.getLog(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public void handlerException(
			HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
		// 1. 로깅 작업
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		LOG.error(errors.toString());
		
//		// 2. 시스템 오류 안내 화면 전환
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("errors", errors.toString());
//		mav.setViewName("error/exception");
//		return mav;
		
		// 예외 발생의 경우 메인 페이지로 보낼껀지 JSON으로 받을 건지 정해야함
		String accept = request.getHeader("accept");
		
		if( accept.matches(".*application/json.*")) {
			// 앞뒤 상관없이 application/json 이라는 문자열 있으면
			// json 응답
			response.setStatus(HttpServletResponse.SC_OK);
			OutputStream out = response.getOutputStream();
			JSONResult jsonResult = JSONResult.fail(errors.toString());
//			out.write(new ObjectMapper().writeValueAsBytes(jsonResult));
			out.write(new ObjectMapper().writeValueAsString(jsonResult).getBytes("utf-8"));
			out.flush();
			out.close();
			
		} else {
			// html 응답
			request.setAttribute("uri", request.getRequestURI());
			request.setAttribute("exception", errors.toString());
			request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);
			
		}
	}
}
