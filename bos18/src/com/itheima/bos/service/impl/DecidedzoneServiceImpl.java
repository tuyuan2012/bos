package com.itheima.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IDecidedzoneDao;
import com.itheima.bos.dao.ISubareaDao;
import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.page.PageBean;
import com.itheima.bos.service.IDecidedzoneService;

@Service
@Transactional
public class DecidedzoneServiceImpl implements IDecidedzoneService{
	@Autowired
	private IDecidedzoneDao decidedzoneDao;
	@Autowired
	private ISubareaDao subareaDao;
	public void save(Decidedzone model, String[] subareaid) {
		decidedzoneDao.save(model);
		for (String sid : subareaid) {
			Subarea subarea = subareaDao.findById(sid);//持久状态
			//model.getSubareas().add(subarea);
			subarea.setDecidedzone(model);//分区关联定区，事务提交时，自动更新数据库
		}
	}
	public void pageQuery(PageBean pageBean) {
		decidedzoneDao.pageQuery(pageBean);
	}

}
