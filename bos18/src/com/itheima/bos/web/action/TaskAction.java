package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Workordermanage;
import com.itheima.bos.service.IWorkordermanageService;
import com.itheima.bos.utils.BOSContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 任务操作
 * @author zhaoqx
 *
 */
@Controller
@Scope("prototype")
public class TaskAction extends ActionSupport{
	@Resource
	private TaskService taskService;
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private IWorkordermanageService workordermanageService;
	/**
	 * 查询组任务
	 */
	public String findGroupTask(){
		TaskQuery query = taskService.createTaskQuery();
		String candidateUser = BOSContext.getCurrentUser().getId();
		query.taskCandidateUser(candidateUser);//候选人过滤
		query.orderByTaskCreateTime().desc();
		List<Task> list = query.list();
		ActionContext.getContext().getValueStack().set("list", list);
		return "grouptasklist";
	}
	
	private String taskId;
	/**
	 * 根据任务ID查询对应业务数据
	 * @throws IOException 
	 */
	public String showData() throws IOException{
		Map<String, Object> map = taskService.getVariables(taskId);
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(map);
		return NONE;
	}
	
	/**
	 * 拾取组任务
	 */
	public String takeTask(){
		String userId = BOSContext.getCurrentUser().getId();
		taskService.claim(taskId, userId);
		return "toGrouptasklist";
	}
	
	/**
	 * 查询当前登录人的个人任务
	 */
	public String findPersonalTask(){
		TaskQuery query = taskService.createTaskQuery();
		String assignee = BOSContext.getCurrentUser().getId();
		query.taskAssignee(assignee);
		query.orderByTaskCreateTime().desc();
		List<Task> list = query.list();
		ActionContext.getContext().getValueStack().set("list", list);
		return "personaltasklist";
	}
	
	private Integer check;//审核是否通过标志位，1：通过 0：不通过
	
	/**
	 * 处理审核工作单任务
	 */
	public String checkWorkOrderManage(){
		//根据任务ID查询任务对象
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		//根据流程实例ID查询流程实例对象
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		//根据流程实例获得业务键
		String workordermanageId = processInstance.getBusinessKey();
		Workordermanage workordermanage = workordermanageService.findById(workordermanageId);
		if(check == null){//跳转到审核页面,展示工作单信息
			ActionContext.getContext().getValueStack().set("map", workordermanage);
			return "checkUI";
		}else{
			//设置流程变量
			taskService.setVariable(taskId, "check", check);
			if(check == 0){
				//如果审核没有通过，将工作单的start属性改为“0”
				workordermanage.setStart("0");
			}
			//将工作单managerCheck属性改为“1”
			workordermanage.setManagerCheck("1");
			workordermanageService.update(workordermanage);
			//办理任务
			taskService.complete(taskId);
			return "toPersonaltasklist";
		}
	}
	
	/**
	 * 办理出库任务
	 */
	public String outStore(){
		taskService.complete(taskId);
		return "toPersonaltasklist";
	}
	
	/**
	 * 办理配送任务
	 */
	public String transferGoods(){
		taskService.complete(taskId);
		return "toPersonaltasklist";
	}
	
	/**
	 * 办理签收任务
	 */
	public String receive(){
		taskService.complete(taskId);
		return "toPersonaltasklist";
	}
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Integer getCheck() {
		return check;
	}

	public void setCheck(Integer check) {
		this.check = check;
	}
}
