package com.itheima.bos.utils;

import org.apache.struts2.ServletActionContext;

import com.itheima.bos.domain.User;

public class BOSContext {
	public static User getCurrentUser(){
		return (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
	}
}
