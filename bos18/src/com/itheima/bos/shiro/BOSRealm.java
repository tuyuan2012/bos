package com.itheima.bos.shiro;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.itheima.bos.dao.IFunctionDao;
import com.itheima.bos.dao.IUserDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;
/**
 * 自定义一个realm
 * @author zhaoqx
 *
 */
public class BOSRealm extends AuthorizingRealm{
	//注入dao
	@Resource
	private IUserDao userDao;
	@Resource
	private IFunctionDao functionDao;
	
	//认证方法
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		//1、用户名不存在异常
		//2、密码错误异常
		UsernamePasswordToken myToken = (UsernamePasswordToken)token;
		String username = myToken.getUsername();//admin
		char[] password = myToken.getPassword();//wwwww
		//根据用户名查询密码
		User user = userDao.findUserByUserName(username);
		if(user == null){
			//账号不存在
			return null;
		}else{
			//账号存在,将从数据库中查询出的密码交给安全管理器比对
			//简单认证信息对象
			AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
			return info;
		}
	}
	
	
	//授权方法
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		//授权信息对象
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//获取当前用户
		User user = (User) principals.getPrimaryPrincipal();
		//根据当前用户查询对应的权限
		if(user.getUsername().equals("admin")){
			//超级管理员,查询所有权限，授予当前用户
			List<Function> list = functionDao.findAll();
			for (Function function : list) {
				info.addStringPermission(function.getCode());
			}
		}else{
			//普通用户，根据角色查询
			List<Function> list = functionDao.findListByUserid(user.getId());
			for (Function function : list) {
				info.addStringPermission(function.getCode());
			}
		}
		return info;
	}


}
