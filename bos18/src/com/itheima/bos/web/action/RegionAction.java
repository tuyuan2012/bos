package com.itheima.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Region;
import com.itheima.bos.page.PageBean;
import com.itheima.bos.service.IRegionService;
import com.itheima.bos.utils.PinYin4jUtils;
import com.itheima.bos.web.action.base.BaseAction;

/**
 * 区域管理Action
 * @author zhaoqx
 *
 */
@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region>{
	//接收上传的文件
	private File myFile;

	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}
	
	/**
	 * 区域数据导入功能
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public String importXls() throws FileNotFoundException, IOException{
		String flag = "1";
		try{
			//使用POI解析Excel文件，将数据导入到数据库中
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(myFile));
			HSSFSheet sheet = workbook.getSheetAt(0);
			List<Region> list = new ArrayList<Region>();
			for (Row row : sheet) {
				int rowNum = row.getRowNum();
				if(rowNum == 0){
					continue;
				}
				String id = row.getCell(0).getStringCellValue();
				String province = row.getCell(1).getStringCellValue();
				String city = row.getCell(2).getStringCellValue();
				String district = row.getCell(3).getStringCellValue();
				String postcode = row.getCell(4).getStringCellValue();
				Region region = new Region(id, province, city, district, postcode, null, null, null);
				
				city = city.substring(0, city.length() - 1);
				String citycode = PinYin4jUtils.hanziToPinyin(city, "");
				region.setCitycode(citycode);
				province = province.substring(0, province.length() - 1);
				district = district.substring(0, district.length() - 1);
				String info = province + city + district;//河北石家庄桥西
				String[] infos = PinYin4jUtils.getHeadByString(info);
				String shortcode = StringUtils.join(infos, "");
				region.setShortcode(shortcode);
				list.add(region);
			}
			regionService.saveBatch(list);
		}catch (Exception e) {
			flag = "0";
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}
	
	/**
	 * 分页查询
	 */
	@RequiresPermissions(value="region")//执行这个方法需要具有region.query这个权限
	//@RequiresRoles(value="admin")
	public String pageQuery(){
		regionService.pageQuery(pageBean);
		String[] excludes = new String[]{"subareas"};
		this.writePageBean2Json(pageBean, excludes );
		return NONE;
	}
	
	private String q;
	
	/**
	 * 查询所有的区域数据，返回json
	 */
	public String listajax(){
		List<Region> list = null;
		if(StringUtils.isNotBlank(q)){
			list = regionService.findByQ(q.trim());
		}else{
			list = regionService.findAll();
		}
		
		String[] excludes = new String[]{"subareas"};
		this.writeList2Json(list, excludes );
		return NONE;
	}

	public void setQ(String q) {
		this.q = q;
	}
}
