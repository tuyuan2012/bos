package com.itheima.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.web.action.base.BaseAction;
import com.opensymphony.xwork2.ActionChainResult;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 流程定义管理
 * 
 * @author zhaoqx
 * 
 */
@Controller
@Scope("prototype")
public class ProcessDefinitionAction extends ActionSupport {
	@Resource
	private RepositoryService repositoryService;

	/**
	 * 查询最新版本的流程定义数据
	 */
	public String list() {
		ProcessDefinitionQuery query = repositoryService
				.createProcessDefinitionQuery();
		query.latestVersion();// 查询最新版本
		List<ProcessDefinition> list = query.list();
		ActionContext.getContext().getValueStack().set("list", list);
		return "pdlist";
	}

	// 接收上传的文件
	private File pdfile;

	/**
	 * 部署流程定义
	 * 
	 * @throws Exception
	 */
	public String deploy() throws Exception {
		DeploymentBuilder deploymentBuilder = repositoryService
				.createDeployment();
		deploymentBuilder.addZipInputStream(new ZipInputStream(
				new FileInputStream(pdfile)));//加载zip文件
		deploymentBuilder.deploy();
		return "toPdlist";
	}

	private String id;//接收流程定义ID
	public String delete(){
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		query.processDefinitionId(id);
		ProcessDefinition processDefinition = query.singleResult();
		//部署ID
		String deploymentId = processDefinition.getDeploymentId();
		try{
			repositoryService.deleteDeployment(deploymentId);
			//删除成功
			return "toPdlist";
		}catch (Exception e) {
			//删除失败，有流程实例在运行
			ActionContext.getContext().getValueStack().set("deltag", "1");
			ProcessDefinitionQuery query2 = repositoryService
					.createProcessDefinitionQuery();
			query2.latestVersion();// 查询最新版本
			List<ProcessDefinition> list = query2.list();
			ActionContext.getContext().getValueStack().set("list", list);
			return "pdlist";
		}
	}
	
	/**
	 * 查看png图片
	 */
	public String showpng(){
		//根据流程定义ID查询对应的png图片输入流
		InputStream pngStream = repositoryService.getProcessDiagram(id);
		ActionContext.getContext().getValueStack().set("pngStream", pngStream);
		return "showpng";
	}
	
	public File getPdfile() {
		return pdfile;
	}

	public void setPdfile(File pdfile) {
		this.pdfile = pdfile;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
