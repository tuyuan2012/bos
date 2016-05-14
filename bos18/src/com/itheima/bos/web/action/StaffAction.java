package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Staff;
import com.itheima.bos.page.PageBean;
import com.itheima.bos.service.IStaffService;
import com.itheima.bos.web.action.base.BaseAction;

/**
 *  取派员管理Action
 * @author zhaoqx
 *
 */
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff>{
	/**
	 * 添加方法
	 */
	public String add(){
		//使用shiro提供的编码方式进行权限校验
		//Subject subject = SecurityUtils.getSubject();
		//subject.checkPermission("noticebill.add");
		staffService.save(model);
		return "list";
	}
	
	/**
	 * 分页查询方法
	 * @throws IOException 
	 */
	public String pageQuery() throws IOException{
		staffService.pageQuery(pageBean);
		//将PageBean对象序列号为json返回
		this.writePageBean2Json(pageBean, new String[]{"detachedCriteria","pageSize","currentPage","decidedzones"});
		return NONE;
	}
	
	//接收取派员ID字符串
	private String ids;
	
	/**
	 * 批量作废方法
	 */
	public String delete(){
		staffService.deleteBatch(ids);
		return "list";
	}

	/**
	 * 根据ID修改取派员信息
	 */
	public String edit(){
		//先根据ID查询数据库中原始数据，再使用model覆盖
		String id = model.getId();
		Staff staff = staffService.findById(id);
		staff.setName(model.getName());
		staff.setTelephone(model.getTelephone());
		staff.setHaspda(model.getHaspda());
		staff.setStandard(model.getStandard());
		staff.setStation(model.getStation());
		staffService.update(staff);
		return "list";
	}
	
	/**
	 * 查询没有作废的取派员，返回json
	 */
	public String listajax(){
		List<Staff> list = staffService.findListNotDelete();
		String[] excludes = new String[]{"decidedzones"};
		this.writeList2Json(list, excludes);
		return NONE;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
}
