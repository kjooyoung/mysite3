package com.douzone.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.UserDao;
import com.douzone.mysite.vo.UserVo;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;

	public void join(UserVo userVo) {
		// 1. DB에 가입 회원 정보 insert
		userDao.insert(userVo);
		// 2. email 주소 확인하는 메일 보내기
	}

	public UserVo login(UserVo userVo) {
		return userDao.get(userVo.getEmail(), userVo.getPassword());
	}
	
	public void modify(UserVo userVo) {
		if("".equals(userVo.getPassword())) {
			userDao.update(userVo.getNo(), userVo.getName(), userVo.getGender());
		} else {
			userDao.updateAll(userVo.getNo(), userVo.getName(), userVo.getPassword(), userVo.getGender());
		}
	}
	
	public UserVo getUser(Long no) {
		return userDao.get(no);
	}
	
}
