package com.itheima.bos.web.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Role;
import com.itheima.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{
	private String ids;//接收权限ID
	public String add(){
		roleService.save(model,ids);
		return "list";
	}
	
	public String pageQuery(){
		roleService.pageQuery(pageBean);
		String[] excludes = new String[]{"functions","users"};
		this.writePageBean2Json(pageBean, excludes);
		return NONE;
	}
	
	public String listajax(){
		List<Role> list = roleService.findAll();
		String[] excludes = new String[]{"functions","users"};
		this.writeList2Json(list, excludes);
		return NONE;
	}
	
	public void setIds(String ids) {
		this.ids = ids;
	}

}
