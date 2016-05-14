package com.itheima.bos.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.crm.CustomerService;
import com.itheima.bos.dao.IDecidedzoneDao;
import com.itheima.bos.dao.INoticebillDao;
import com.itheima.bos.dao.IWorkbillDao;
import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.domain.Noticebill;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.domain.User;
import com.itheima.bos.domain.Workbill;
import com.itheima.bos.service.INoticebillService;
import com.itheima.bos.utils.BOSContext;

@Service
@Transactional
public class NoticebillServiceImpl implements INoticebillService{
	@Autowired
	private INoticebillDao noticebillDao;
	@Autowired
	private IDecidedzoneDao decidedzoneDao;
	@Autowired
	private IWorkbillDao workbillDao;
	//注入代理对象，调用crm
	@Autowired
	private CustomerService customerService;
	/**
	 *  保存业务通知单、尝试自动分单
	 */
	public void save(Noticebill model) {
		User user = BOSContext.getCurrentUser();
		model.setUser(user);//当前登录用户
		noticebillDao.save(model);//持久对象
		//尝试自动分单
		String pickaddress = model.getPickaddress();//XXX省XXX市XXX区XXX大街XXX号
		//调用crm服务，根据取件地址匹配定区ID
		String decidedzoneId = customerService.findDecidedzoneIdByAddress(pickaddress);
		if(decidedzoneId != null){
			//查询到定区ID，可以自动分单
			//设置分单类型为“自动”
			model.setOrdertype("自动分单");
			//根据定区ID查询定区，根据定区查询取派员
			Decidedzone decidedzone = decidedzoneDao.findById(decidedzoneId);
			Staff staff = decidedzone.getStaff();
			//业务通知单关联取派员
			model.setStaff(staff);
			//为取派员创建一个工单
			Workbill workbill = new Workbill();
			workbill.setAttachbilltimes(0);//追单次数
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));//当前系统时间
			workbill.setNoticebill(model);//工单关联业务通知单
			workbill.setPickstate("未取件");
			workbill.setRemark(model.getRemark());//备注
			workbill.setStaff(staff);//工单关联取派员
			workbill.setType("新单");
			workbillDao.save(workbill);//保存工单
			//调用短信服务平台，为取派员发送短信
		}else{
			//没有查询到定区ID，转入人工调度
			model.setOrdertype("人工分单");
		}
	}
}
