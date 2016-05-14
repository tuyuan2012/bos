package com.itheima.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sun.reflect.generics.visitor.Reifier;

import com.itheima.bos.dao.IRegionDao;
import com.itheima.bos.domain.Region;
import com.itheima.bos.page.PageBean;
import com.itheima.bos.service.IRegionService;

/**
 * 区域管理Service
 * @author zhaoqx
 *
 */
@Service
@Transactional
public class RegionServiceImpl implements IRegionService{
	@Autowired
	private IRegionDao regionDao;
	
	public void saveBatch(List<Region> list) {
		for (Region region : list) {
			regionDao.saveOrUpdate(region);
		}
	}

	public void pageQuery(PageBean pageBean) {
		regionDao.pageQuery(pageBean);
	}

	public List<Region> findAll() {
		return regionDao.findAll();
	}

	public List<Region> findByQ(String q) {
		return regionDao.findByQ(q);
	}

}
