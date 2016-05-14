package com.itheima.bos.web.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;
import com.itheima.bos.utils.BOSContext;
import com.itheima.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function>{
	public String pageQuery(){
		pageBean.setCurrentPage(Integer.parseInt(model.getPage()));
		functionService.pageQuery(pageBean);
		String[] excludes = new String[]{"roles","children","parent","detachedCriteria"};
		this.writePageBean2Json(pageBean, excludes);
		return NONE;
	}
	
	public String listajax(){
		List<Function> list = functionService.findAll();
		String[] excludes = new String[]{"roles","children","parent"};
		this.writeList2Json(list, excludes);
		return NONE;
	}
	
	public String add(){
		functionService.save(model);
		return "list";
	}
	
	/**
	 * 根据登录人查询菜单
	 */
	public String findMenu(){
		User user = BOSContext.getCurrentUser();
		List<Function> list = functionService.findMenu(user);
		String[] excludes = new String[]{"children","roles"};
		this.writeList2Json(list, excludes );
		return NONE;
	}
}
