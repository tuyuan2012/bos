package com.itheima.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IFunctionDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;
import com.itheima.bos.page.PageBean;
import com.itheima.bos.service.IFunctionService;

@Service
@Transactional
public class FunctionServiceImpl implements IFunctionService{
	@Autowired
	private IFunctionDao functionDao;

	public void pageQuery(PageBean pageBean) {
		functionDao.pageQuery(pageBean);
	}

	public List<Function> findAll() {
		return functionDao.findAll();
	}

	public void save(Function model) {
		Function parent = model.getParent();
		if(parent != null && parent.getId() != null && parent.getId().equals("")){
			model.setParent(null);
		}
		functionDao.save(model);
	}

	public List<Function> findMenu(User user) {
		List<Function> list = null;
		if(user.getUsername().equals("admin")){
			list = functionDao.findAllMenu();
		}else{
			list = functionDao.findMenuByUserid(user.getId());
		}
		return list;
	}
}
