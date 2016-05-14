package com.itheima.bos.service.impl;

import javax.annotation.Resource;

import org.activiti.engine.IdentityService;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IRoleDao;
import com.itheima.bos.dao.IUserDao;
import com.itheima.bos.domain.Role;
import com.itheima.bos.domain.User;
import com.itheima.bos.page.PageBean;
import com.itheima.bos.service.IUserService;
import com.itheima.bos.utils.MD5Utils;

/**
 * 用户管理Service
 * @author zhaoqx
 *
 */
@Service
@Transactional
public class UserServiceImpl implements IUserService{
	//注入dao
	@Resource
	private IUserDao userDao;
	@Resource
	private IRoleDao roleDao;
	@Resource
	private IdentityService identityService;
	public User login(User model) {
		String password = model.getPassword();
		password = MD5Utils.md5(password);
		return userDao.findUserByUserNameAndPassword(model.getUsername(),password);
	}
	
	//修改密码
	public void editPassword(User model) {
		String id = model.getId();
		String password = model.getPassword();
		password = MD5Utils.md5(password);
		userDao.executeUpdate("editPassword", password,id);
	}

	/**
	 * 保存用户时，同步数据到activiti的act_id_user act_id_membership
	 */
	public void save(User user, String[] roleIds) {
		String password = user.getPassword();
		password = MD5Utils.md5(password);
		user.setPassword(password);
		userDao.save(user);//持久对象
		
		org.activiti.engine.identity.User actUser = new UserEntity(user.getId());
		identityService.saveUser(actUser);
		
		for (String roleId : roleIds) {
			Role role = roleDao.findById(roleId);//持久对象
			//role.getUsers().add(user);
			user.getRoles().add(role);//用户关联角色----user_role
			identityService.createMembership(actUser.getId(), role.getName());
		}
	}

	public void pageQuery(PageBean pageBean) {
		userDao.pageQuery(pageBean);
	}

}
