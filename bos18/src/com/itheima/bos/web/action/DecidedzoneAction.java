package com.itheima.bos.web.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.crm.domain.Customer;

import com.itheima.bos.crm.CustomerService;
import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.web.action.base.BaseAction;

/**
 * 定区管理
 * @author zhaoqx
 *
 */
@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone>{
	//接收分区ID
	private String[] subareaid;
	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}
	/**
	 * 添加分区
	 */
	public String add(){
		decidedzoneService.save(model,subareaid);
		return "list";
	}
	
	/**
	 * 分页查询方法
	 */
	public String pageQuery(){
		decidedzoneService.pageQuery(pageBean);
		String[] excludes = new String[]{"detachedCriteria","pageSize","currentPage","subareas","decidedzones"};
		this.writePageBean2Json(pageBean, excludes);
		return NONE;
	}
	
	//注入代理对象，远程调用crm服务
	@Resource
	private CustomerService customerService;
	
	/**
	 * 查询没有关联到定区的客户
	 */
	public String findnoassociationCustomers(){
		List<Customer> list = customerService.findnoassociationCustomers();
		String[] excludes = new String[]{};
		this.writeList2Json(list, excludes);
		return NONE;
	}
	
	/**
	 * 查询已经关联到指定定区的客户
	 */
	public String findhasassociationCustomers(){
		List<Customer> list = customerService.findhasassociationCustomers(model.getId());
		String[] excludes = new String[]{};
		this.writeList2Json(list, excludes);
		return NONE;
	}
	
	private Integer[] customerIds;
	
	/**
	 * 定区关联客户
	 */
	public String assigncustomerstodecidedzone(){
		customerService.assignCustomersToDecidedZone(customerIds, model.getId());
		return "list";
	}
	public Integer[] getCustomerIds() {
		return customerIds;
	}
	public void setCustomerIds(Integer[] customerIds) {
		this.customerIds = customerIds;
	}
}
