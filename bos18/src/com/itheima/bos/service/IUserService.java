package com.itheima.bos.service;

import com.itheima.bos.domain.User;
import com.itheima.bos.page.PageBean;

public interface IUserService {

	public User login(User model);

	public void editPassword(User model);

	public void save(User model, String[] roleIds);

	public void pageQuery(PageBean pageBean);

}
