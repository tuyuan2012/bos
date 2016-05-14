package com.itheima.bos.web.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.utils.ActivitiUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 流程实例管理
 * 
 * @author zhaoqx
 * 
 */
@Controller
@Scope("prototype")
public class ProcessInstanceAction extends ActionSupport {
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private RepositoryService repositoryService;

	/**
	 * 查询流程实例
	 */
	public String list() {
		ProcessInstanceQuery query = runtimeService
				.createProcessInstanceQuery();
		query.orderByProcessInstanceId().desc();
		List<ProcessInstance> list = query.list();
		ActionContext.getContext().getValueStack().set("list", list);
		return "list";
	}

	private String id;// 流程实例ID

	/**
	 * 根据流程实例ID查询对应的流程变量
	 * 
	 * @throws IOException
	 */
	public String findData() throws IOException {
		Map<String, Object> map = runtimeService.getVariables(id);
		ServletActionContext.getResponse().setContentType(
				"text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(map);
		return NONE;
	}

	/**
	 * 根据流程实例ID查询部署ID、png图片名称、坐标
	 * 
	 * @return
	 */
	public String showPng() {
		Map<String, Object> map = ActivitiUtils.findInfo(runtimeService, repositoryService, id);
		ActionContext.getContext().getValueStack().push(map);
		return "showPng";
	}

	private String deploymentId;
	private String imageName;
	
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	// 根据部署ID和图片名称查询输入流
	public String viewImage() {
		InputStream pngStream = repositoryService.getResourceAsStream(deploymentId, imageName);
		ActionContext.getContext().getValueStack().set("pngStream", pngStream);
		return "viewImage";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
