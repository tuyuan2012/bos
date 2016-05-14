package com.itheima.bos.web.action;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.crm.domain.Customer;

import com.itheima.bos.crm.CustomerService;
import com.itheima.bos.domain.Noticebill;
import com.itheima.bos.web.action.base.BaseAction;

/**
 * 业务通知单操作 
 * @author zhaoqx
 *
 */
@Controller
@Scope("prototype")
public class NoticebillAction extends BaseAction<Noticebill>{
	//注入代理对象，可以远程访问crm
	@Resource
	private CustomerService customerService;
	
	public String findCustomerByTelephone(){
		Customer customer = customerService.findCustomerByPhoneNumber(model.getTelephone());
		String[] excludes = new String[]{};
		this.writeObject2Json(customer, excludes);
		return NONE;
	}
	
	/**
	 * 添加一个业务通知单,尝试自动分单
	 */
	public String add(){
		noticebillService.save(model);
		return "addUI";
	}
}
