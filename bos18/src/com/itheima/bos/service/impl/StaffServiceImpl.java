package com.itheima.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IStaffDao;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.page.PageBean;
import com.itheima.bos.service.IStaffService;

/**
 * 取派员管理Service
 * @author zhaoqx
 *
 */
@Service
@Transactional
public class StaffServiceImpl implements IStaffService{
	//注入dao
	@Autowired
	private IStaffDao staffDao;
	public void save(Staff model) {
		staffDao.save(model);
	}
	public void pageQuery(PageBean pageBean) {
		staffDao.pageQuery(pageBean);
	}
	public void deleteBatch(String ids) {//1,2,3,4
		String[] sids = ids.split(",");
		for (String id : sids) {
			staffDao.executeUpdate("staff.delete", id);
		}
	}
	public Staff findById(String id) {
		return staffDao.findById(id);
	}
	public void update(Staff staff) {
		staffDao.update(staff);
	}
	
	/**
	 * 查询未作废的取派员
	 */
	public List<Staff> findListNotDelete() {
		DetachedCriteria dc = DetachedCriteria.forClass(Staff.class);
		dc.add(Restrictions.eq("deltag", "0"));
		return staffDao.findByCriteria(dc);
	}
}
