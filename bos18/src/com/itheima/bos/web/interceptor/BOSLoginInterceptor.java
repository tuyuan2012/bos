package com.itheima.bos.web.interceptor;

import org.apache.struts2.ServletActionContext;

import com.itheima.bos.domain.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 自定义拦截器，实现未登录自动跳转到登录页面
 * @author zhaoqx
 *
 */
public class BOSLoginInterceptor extends MethodFilterInterceptor{
	protected String doIntercept(ActionInvocation ai) throws Exception {
		ActionProxy proxy = ai.getProxy();
		String url = proxy.getNamespace() + proxy.getActionName();
		System.out.println("客户端访问路径：" + url );
		User user = (User) ServletActionContext.getContext().getSession()
				.get("loginUser");
		if(user == null){
			//用户没有登录,跳转回登录页面
			return "login";
		}
		return ai.invoke();//放行
	}
}
