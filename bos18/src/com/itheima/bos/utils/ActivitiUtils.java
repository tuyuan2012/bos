package com.itheima.bos.utils;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

public class ActivitiUtils {
	public static Map<String, Object> findInfo(RuntimeService runtimeService,
			RepositoryService repositoryService, String processinstanceId) {
		// 根据流程实例ID查询流程实例对象
		ProcessInstance processInstance = runtimeService
				.createProcessInstanceQuery()
				.processInstanceId(processinstanceId).singleResult();
		// 根据流程实例获得流程定义ID
		String processDefinitionId = processInstance.getProcessDefinitionId();
		// 根据ID查询流程定义
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).singleResult();
		// 部署ID
		String deploymentId = processDefinition.getDeploymentId();
		// 图片名称
		String pngName = processDefinition.getDiagramResourceName();
		// 获得当前流程实例运行到那个任务了
		String activityId = processInstance.getActivityId();// usertask1
		// 根据流程定义ID查询流程定义对象，查询bytearray
		ProcessDefinitionEntity pd = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(processDefinitionId);
		ActivityImpl activityImpl = pd.findActivity(activityId);
		int x = activityImpl.getX();
		int y = activityImpl.getY();
		int width = activityImpl.getWidth();
		int height = activityImpl.getHeight();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deploymentId", deploymentId);
		map.put("pngName", pngName);
		map.put("x", x);
		map.put("y", y);
		map.put("width", width);
		map.put("height", height);

		return map;
	}
}
