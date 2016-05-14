package com.itheima.bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.itheima.bos.dao.base.IBaseDao;
import com.itheima.bos.page.PageBean;

/**
 * 持久层通用实现
 * 
 * @author zhaoqx
 * 
 * @param <T>
 */
public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {
	@Resource
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	// 实体类型
	private Class<T> entityClass;

	// 在父类（BaseDaoImpl）构造方法中动态获取实体的类型
	public BaseDaoImpl() {
		// 获得父类（BaseDaoImpl）类型
		ParameterizedType genericSuperclass = (ParameterizedType) this
				.getClass().getGenericSuperclass();
		// 获得父类上的泛型数组
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		entityClass = (Class<T>) actualTypeArguments[0];
	}

	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
	}

	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}

	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	public T findById(Serializable id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	public List<T> findAll() {
		String hql = "FROM  " + entityClass.getSimpleName();
		return this.getHibernateTemplate().find(hql);
	}

	/**
	 * 执行任意更新
	 */
	public void executeUpdate(String namedQuery, Object... args) {
		Session session = this.getSession();// 从本地线程获得的session
		Query query = session.getNamedQuery(namedQuery);
		if (args != null && args.length > 0) {
			int i = 0;
			for (Object arg : args) {
				query.setParameter(i++, arg);
			}
		}
		query.executeUpdate();
	}

	/**
	 * 通用分页查询方法
	 */
	public void pageQuery(PageBean pageBean) {
		int currentPage = pageBean.getCurrentPage();
		int pageSize = pageBean.getPageSize();
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		// 分页查询，查询两部分信息，总记录数total，当前页展示的数据集合rows
		// 指定Hibernate框架发出select count(*) from bc_staff...
		detachedCriteria.setProjection(Projections.rowCount());
		List<Long> countList = this.getHibernateTemplate().findByCriteria(
				detachedCriteria);
		Long total = countList.get(0);//总记录数
		pageBean.setTotal(total.intValue());
		// 指定Hibernate框架发出select * from xxx...
		detachedCriteria.setProjection(null);
		//将对象之前的对象关系重置回来Staff---->>bc_staff
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		int firstResult = (currentPage - 1) * pageSize;
		int maxResults = pageSize;
		List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		pageBean.setRows(rows);
	}

	public void saveOrUpdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	public List<T> findByCriteria(DetachedCriteria dc) {
		return this.getHibernateTemplate().findByCriteria(dc);
	}

}
