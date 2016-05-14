package com.itheima.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IRoleDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.Role;
import com.itheima.bos.page.PageBean;
import com.itheima.bos.service.IRoleService;
import com.mchange.v1.identicator.Identicator;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService{
	@Resource
	private IRoleDao roleDao;
	@Resource
	private IdentityService identityService;
	
	/**
	 * 添加角色，同时将角色数据同步到activiti的组表
	 */
	public void save(Role role, String ids) {//1,2,3,4,5
		roleDao.save(role);//持久状态对象
		
		Group group = new GroupEntity(role.getName());
		identityService.saveGroup(group);
		
		String[] functionIds = ids.split(",");
		for (String fid : functionIds) {
			Function function = new Function();
			function.setId(fid);//托管对象
			role.getFunctions().add(function);//角色和权限建立关联
		}
	}
	
	public void pageQuery(PageBean pageBean) {
		roleDao.pageQuery(pageBean);
	}

	public List<Role> findAll() {
		return roleDao.findAll();
	}
}
