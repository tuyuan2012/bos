package com.itheima.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.IFunctionDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
import com.itheima.bos.domain.Function;

@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements IFunctionDao{
	/**
	 * 根据用户ID查询对应的权限
	 */
	public List<Function> findListByUserid(String userid) {
		String hql = "select f FROM Function f left outer join f.roles r left " +
				"outer join r.users u where u.id = ?";
		return this.getHibernateTemplate().find(hql,userid);
	}

	/**
	 * 查询所有的菜单数据
	 */
	public List<Function> findAllMenu() {
		String hql = "FROM Function f where f.generatemenu = '1' order by f.zindex desc";
		return this.getHibernateTemplate().find(hql);
	}
	
	/**
	 * 根据用户ID查询对应的菜单数据
	 */
	public List<Function> findMenuByUserid(String id) {
		String hql = "select distinct f FROM Function f left outer join f.roles r left " +
				"outer join r.users u where u.id = ? and f.generatemenu = '1' " +
				"order by f.zindex desc";
		return this.getHibernateTemplate().find(hql,id);
	}

}
