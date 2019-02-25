package com.douzone.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthLogoutInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		if(session != null) {
			// logout처리
			session.removeAttribute("authuser");
			// browser 메모리에 있는 session id 날림
			session.invalidate();
		}
		response.sendRedirect(request.getContextPath()+"/");
		
		return false;
	}
	
}
