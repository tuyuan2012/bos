package com.itheima.bos.dao.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.itheima.bos.page.PageBean;

/**
 * 抽取持久层公共方法
 * @author zhaoqx
 *
 * @param <T>
 */
public interface IBaseDao<T> {
	public void save(T entity);
	public void delete(T entity);
	public void update(T entity);
	public void saveOrUpdate(T entity);
	public T findById(Serializable id);
	//根据条件查询
	public List<T> findByCriteria(DetachedCriteria dc);
	public List<T> findAll();
	//执行任意更新语句
	public void executeUpdate(String namedQuery,Object ...objects);
	//通用分页查询方法 
	public void pageQuery(PageBean pageBean);
}
