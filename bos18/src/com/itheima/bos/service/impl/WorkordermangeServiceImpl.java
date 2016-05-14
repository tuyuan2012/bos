package com.itheima.bos.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IWorkordermanageDao;
import com.itheima.bos.domain.Workordermanage;
import com.itheima.bos.service.IWorkordermanageService;

@Service
@Transactional
public class WorkordermangeServiceImpl implements IWorkordermanageService{
	@Resource
	private IWorkordermanageDao workordermanageDao;

	public void save(Workordermanage model) {
		workordermanageDao.saveOrUpdate(model);
	}

	/**
	 * 查询没有启动流程实例的工作单
	 */
	public List<Workordermanage> findListNotStart() {
		DetachedCriteria dc = DetachedCriteria.forClass(Workordermanage.class);
		dc.add(Restrictions.eq("start", "0"));
		dc.addOrder(Order.desc("id"));
		return workordermanageDao.findByCriteria(dc);
	}

	@Resource
	private RuntimeService runtimeService;
	/**
	 * 启动物流配送流程
	 */
	public void start(String id) {
		//修改工作单start标志位为1
		Workordermanage workordermanage = workordermanageDao.findById(id);
		workordermanage.setStart("1");
		String processDefinitionKey = "transfer";
		String businessKey = id;//业务键值为当前工作单的ID
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("业务数据", workordermanage);
		runtimeService.startProcessInstanceByKey(processDefinitionKey , businessKey, variables);
	}

	public Workordermanage findById(String id) {
		return workordermanageDao.findById(id);
	}

	public void update(Workordermanage workordermanage) {
		workordermanageDao.update(workordermanage);
	}
}
