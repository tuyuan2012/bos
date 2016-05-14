package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Region;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.utils.FileUtils;
import com.itheima.bos.web.action.base.BaseAction;

/**
 * 分区管理Action
 * @author zhaoqx
 *
 */
@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea>{
	/**
	 * 添加分区
	 */
	public String add(){
		subareaService.save(model);
		return "list";
	}
	
	/**
	 * 分页查询
	 */
	public String pageQuery(){
		//地址关键字
		String addresskey = model.getAddresskey();
		//动态添加过滤条件
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		if(StringUtils.isNotBlank(addresskey)){
			//添加过滤条件，根据地址关键字模糊查询
			dc.add(Restrictions.like("addresskey", "%"+addresskey.trim()+"%"));
		}
		Region region = model.getRegion();
		if(region != null){
			String province = region.getProvince();//省
			String city = region.getCity();//市
			String district = region.getDistrict();//区
			dc.createAlias("region", "r");//创建一个别名
			if(StringUtils.isNotBlank(province)){
				//根据省进行模糊查询
				dc.add(Restrictions.like("r.province", "%"+province.trim()+"%"));
			}
			if(StringUtils.isNotBlank(city)){
				//根据市进行模糊查询
				dc.add(Restrictions.like("r.city", "%"+city.trim()+"%"));
			}
			if(StringUtils.isNotBlank(district)){
				//根据区进行模糊查询
				dc.add(Restrictions.like("r.district", "%"+district.trim()+"%"));
			}
		}
		subareaService.pageQuery(pageBean);
		String[] excludes = new String[]{"detachedCriteria","pageSize","currentPage","decidedzone","subareas"};
		this.writePageBean2Json(pageBean, excludes );
		return NONE;
	}
	
	/**
	 * 导出分区数据到Excel文件，提供下载
	 * @throws IOException 
	 */
	public String exportXls() throws IOException{
		List<Subarea> list = subareaService.findAll();
		//使用POI将查询到的数据写到一个Excel文件中
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("分区数据");
		HSSFRow headRow = sheet.createRow(0);//标题行
		headRow.createCell(0).setCellValue("地址关键字");
		headRow.createCell(1).setCellValue("地址信息");
		headRow.createCell(2).setCellValue("省市区");
		for (Subarea subarea : list) {
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
			dataRow.createCell(0).setCellValue(subarea.getAddresskey());
			dataRow.createCell(1).setCellValue(subarea.getPosition());
			Region region = subarea.getRegion();
			dataRow.createCell(2).setCellValue(region.getName());
		}
		String filename = "分区数据.xls";
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		filename = FileUtils.encodeDownloadFilename(filename, agent);
		String contentType = ServletActionContext.getServletContext().getMimeType(filename);
		//文件下载,一个流、两个头
		ServletActionContext.getResponse().setContentType(contentType);
		ServletActionContext.getResponse().setHeader("content-disposition", "attachement;filename="+filename);
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
		workbook.write(out);
		out.close();
		return NONE;
	}
	
	/**
	 * 查询没有分配到定区的分区数据，返回json
	 */
	public String listajax(){
		List<Subarea> list = subareaService.findListNotAssociation();
		String[] excludes = new String[]{"decidedzone","region"};
		this.writeList2Json(list, excludes );
		return NONE;
	}
}
