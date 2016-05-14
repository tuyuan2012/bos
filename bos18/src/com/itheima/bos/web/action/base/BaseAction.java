package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.itheima.bos.domain.Staff;
import com.itheima.bos.page.PageBean;
import com.itheima.bos.service.IDecidedzoneService;
import com.itheima.bos.service.IFunctionService;
import com.itheima.bos.service.INoticebillService;
import com.itheima.bos.service.IRegionService;
import com.itheima.bos.service.IRoleService;
import com.itheima.bos.service.IStaffService;
import com.itheima.bos.service.ISubareaService;
import com.itheima.bos.service.IWorkordermanageService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 抽取表现层
 * 
 * @author zhaoqx
 * 
 * @param <T>
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	protected T model;// 模型对象

	public T getModel() {
		return model;
	}
	
	@Autowired
	protected IStaffService staffService;
	@Resource
	protected IRegionService regionService;
	@Resource
	protected ISubareaService subareaService;
	@Resource
	protected IDecidedzoneService decidedzoneService;
	@Resource
	protected INoticebillService noticebillService;
	@Resource
	protected IWorkordermanageService workordermanageService;
	@Resource
	protected IFunctionService functionService;
	@Resource
	protected IRoleService roleService;
	
	protected PageBean pageBean = new PageBean();
	protected DetachedCriteria detachedCriteria = null;
	public void setPage(int page) {
		pageBean.setCurrentPage(page);
	}
	public void setRows(int rows) {
		pageBean.setPageSize(rows);
	}
	// 在构造方法中动态获得实体类型，通过反射创建模型对象
	public BaseAction() {
		ParameterizedType genericSuperclass = null;
		Type genericSuperclass2 = this.getClass().getGenericSuperclass();
		if(genericSuperclass2 instanceof ParameterizedType){
			genericSuperclass = (ParameterizedType)genericSuperclass2;
		}else{
			//this为shiro对应的代理对象
			genericSuperclass = (ParameterizedType) this.getClass().getSuperclass().getGenericSuperclass();
		}
		//获得BaseAction类定义的泛型数组
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		Class<T> entityClass = (Class<T>) actualTypeArguments[0];
		detachedCriteria = DetachedCriteria.forClass(entityClass);
		pageBean.setDetachedCriteria(detachedCriteria);
		try {
			model = entityClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将PageBean对象转为json，返回客户端
	 */
	public void writePageBean2Json(PageBean pageBean,String[] excludes){
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		JSONObject jsonObject = JSONObject.fromObject(pageBean,jsonConfig);
		String json = jsonObject.toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将Object对象转为json，返回客户端
	 */
	public void writeObject2Json(Object o,String[] excludes){
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		JSONObject jsonObject = JSONObject.fromObject(o,jsonConfig);
		String json = jsonObject.toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将List对象转为json，返回客户端
	 */
	public void writeList2Json(List list,String[] excludes){
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		JSONArray jsonObject = JSONArray.fromObject(list,jsonConfig);
		String json = jsonObject.toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
