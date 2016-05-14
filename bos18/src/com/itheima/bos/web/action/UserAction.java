package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.crm.domain.Customer;

import com.itheima.bos.crm.CustomerService;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.IUserService;
import com.itheima.bos.utils.MD5Utils;
import com.itheima.bos.web.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;

@Controller//("abc")
@Scope("prototype")
public class UserAction extends BaseAction<User>{
	@Resource
	private IUserService userService;
	
	//接收验证码
	private String checkcode;
	
	//注入代理对象，可以远程调用crm
	//@Resource
	//private CustomerService customerService;
	/**
	 * 登录方法
	 */
	public String login_bak(){
		/*List<Customer> list = customerService.findnoassociationCustomers();
		for (Customer customer : list) {
			System.out.println(customer);
		}*/
		
		//从session中获取生成的验证码
		String key = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		//判断用户输入的验证码是否正确
		if(StringUtils.isNotBlank(checkcode) && checkcode.equals(key)){//验证码正确
				//验证码输入正确
				//校验用户名和密码
				User user = userService.login(model);
				if(user != null){
					//登录成功,将当前用户放入session，跳转到系统首页
					ActionContext.getContext().getSession().put("loginUser", user);
					return "home";
				}else{
					//登录失败,设置错误提示信息，跳转到登录页面
					this.addActionError(this.getText("loginError"));
					return "login";
				}
		}else{//验证码错误
			//登录失败,设置错误提示信息，跳转到登录页面
			this.addActionError(this.getText("checkcodeError"));
			return "login";
		}
	}
	
	/**
	 * 使用shiro的认证方式进行认证
	 * @return
	 */
	public String login(){
		String key = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		//判断用户输入的验证码是否正确
		if(StringUtils.isNotBlank(checkcode) && checkcode.equals(key)){//验证码正确
				Subject subject = SecurityUtils.getSubject();//获得当前用户，状态为“未认证”
				String password = MD5Utils.md5(model.getPassword());
				AuthenticationToken token = new UsernamePasswordToken(model.getUsername(), password);
				try{
					subject.login(token);//认证
					User user = (User) subject.getPrincipal();
					ActionContext.getContext().getSession().put("loginUser", user);
					return "home";
				}catch (UnknownAccountException e) {//账号不存在异常
					e.printStackTrace();
					this.addActionError("账号不存在！");
					return "login";
				}catch (IncorrectCredentialsException e) {//密码错误异常
					e.printStackTrace();
					this.addActionError("密码错误！");
					return "login";
				}
		}else{//验证码错误
			//登录失败,设置错误提示信息，跳转到登录页面
			this.addActionError(this.getText("checkcodeError"));
			return "login";
		}
	}
	
	/**
	 * 用户注销功能
	 */
	public String logout(){
		ServletActionContext.getRequest().getSession().invalidate();
		return "login";
	}
	
	/**
	 * 修改密码
	 * @throws IOException 
	 */
	public String editPassword() throws IOException{
		User user = (User) ActionContext.getContext().getSession().get("loginUser");
		model.setId(user.getId());
		String flag = "0";
		try{
			userService.editPassword(model);
		}catch (Exception e) {
			flag = "1";
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}
	
	private String[] roleIds;//接收角色ID
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	public String add(){
		userService.save(model,roleIds);
		return "list";
	}
	
	public String pageQuery(){
		userService.pageQuery(pageBean);
		String[] excludes = new String[]{"roles"};
		this.writePageBean2Json(pageBean, excludes );
		return NONE;
	}
	
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	
}
