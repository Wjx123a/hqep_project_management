package com.hqep.dataSharingPlatform.interface_serv.action;


import com.hqep.dataSharingPlatform.interface_serv.annotation.RequestLog;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: java类作用描述
 * @Author: shaowenqiang
 * @CreateDate: 2021/3/23$ 13:30$
 * @Version: 1.0
 */
@RequestMapping("/activiti")
@RestController
public class Activiti6Manage {
    private static Logger logger = LoggerFactory.getLogger(Activiti6Manage.class);
    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    /**
     * TODO 部署默认流程
     * /activiti/deploy
     */
    @RequestMapping("/deploy")
    public void createActivitiTask() {
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        Deployment d = processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("/processes/负面清单审批流程.bpmn")
                .addClasspathResource("/processes/负面清单审批流程.png")
                .deploy();
        logger.info(d.getId());
        logger.info("部署流程成功");
    }


    /**
     * TODO 部署默认流程成功后，可以启动这个流程
     * /activiti/startActiviti
     */
    @RequestMapping("/startActiviti")
    public void startActiviti() {
        //Id就是act_re_procdef表中的ID_

        identityService.setAuthenticatedUserId("发起人");
        String entityId = "entityId";
        Map<String, Object> variables = new LinkedHashMap<>();
        variables.put("userId", "userId");
        variables.put("申请原因", "123");
        ProcessInstance processInstance
                = runtimeService.startProcessInstanceByKey("myProcess_1",
                entityId, variables);

        logger.info("启动流程成功");
    }

    /**
     * TODO 完成流程中某个人物节点
     *
     * @Return void
     * @Exception /activiti/completeSjzxsp?taskId=2502
     */
    @RequestMapping("/completeSjzxsp")
    public void completeSjzxsp(String taskId) {
        //查看act_ru_task表的ID_
//        将当前用户 UserId 设置审批人
        identityService.setAuthenticatedUserId("审批人");
        processEngine.getTaskService()
                .setAssignee(taskId, "审批人");
//        完成当前节点
        processEngine.getTaskService()
                .complete(taskId);
    }

    /*
     * TODO 查询受理人老王需要审批的流程，创建一个查询，根据创建时间倒叙、受理人是老王的流程
     * @Param []
     * @Return void
     * @Exception
     * /activiti/queryAssineeApprove
     */
//    @RequestLog("查询受理人老王需要审批的流程")
    @RequestMapping("/queryAssineeApprove")
    public void queryAssineeApprove() {
        List<Task> taskList = processEngine.getTaskService()
                .createTaskQuery()
                .orderByTaskCreateTime()
                .desc()
                .list();
        taskList.forEach(System.out::println);
        taskList.stream().forEach((k) -> {
            String taskId = k.getId();
            System.out.print(k + " ");
            taskService.setVariableLocal(taskId, taskId + "变量key", taskId + "变量value");
            System.out.print(taskService.getVariable(taskId, taskId + "变量key"));
            identityService.setAuthenticatedUserId("审批人:" + taskId);
            processEngine.getTaskService()
                    .setAssignee(taskId, "审批人:" + taskId);
//        完成当前节点
            processEngine.getTaskService()
                    .complete(taskId);
        });
    }

    /**
     * 查询历史流程实例
     */
    @RequestMapping("/queryHistoricProcessInstance")
    public void queryHistoricProcessInstance() {
        List<HistoricProcessInstance> hisList = historyService.createHistoricProcessInstanceQuery().orderByProcessInstanceStartTime()
                .listPage(0, 1000);
        hisList.stream().forEach((hpi) -> {
            System.out.println("流程定义ID：" + hpi.getProcessDefinitionId());
            System.out.println("流程实例ID：" + hpi.getId());
            System.out.println("开始时间：" + hpi.getStartTime());
            System.out.println("结束时间：" + hpi.getEndTime());
            System.out.println("流程持续时间：" + hpi.getDurationInMillis());
            System.out.println("=======================================");
        });
    }

    /**
     * 某一次流程执行了多少步
     */
    @RequestMapping("/queryHistoricActivitiInstance")
    public void queryHistoricActivitiInstance(String processInstanceId) {
        List<HistoricActivityInstance> list = processEngine.getHistoryService()
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        list.stream().forEach((hai) -> {
            System.out.println(hai.getId());
            System.out.println("步骤ID：" + hai.getActivityId());
            System.out.println("步骤名称：" + hai.getActivityName());
            System.out.println("执行人：" + hai.getAssignee());
            System.out.println("====================================");
        });
    }

    /**
     * 某一次流程的执行经历的多少任务
     */
    @RequestMapping("/queryHistoricTask")
    public void queryHistoricTask(String processInstanceId) {
        List<HistoricTaskInstance> list = processEngine.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        list.stream().forEach((hti) -> {
            System.out.print("taskId:" + hti.getId() + "，");
            System.out.print("name:" + hti.getName() + "，");
            System.out.print("pdId:" + hti.getProcessDefinitionId() + "，");
            System.out.print("assignee:" + hti.getAssignee() + "，");
        });
    }

    /**
     * 某一次流程的执行时设置的流程变量
     */
    @RequestMapping("/queryHistoricVariables")
    public void queryHistoricVariables(String processInstanceId) {
        List<HistoricVariableInstance> list = processEngine.getHistoryService()
                .createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        list.stream().forEach((hvi) -> {
            System.out.print("piId:" + hvi.getProcessInstanceId() + "，");
            System.out.print("variablesName:" + hvi.getVariableName() + "，");
            System.out.println("variablesValue:" + hvi.getValue() + ";");
        });
    }

    //    activiti获取个人历史任务businessKey
    @RequestMapping("/queryHistryOfPerson")
    public void queryHistryOfPerson() {
        //历史任务实例
        List<HistoricTaskInstance> historicTaskInstanceList = historyService // 历史相关Service
                .createHistoricTaskInstanceQuery() // 创建历史任务实例查询
                .finished() // 查询已经完成的任务
                .list();
        if (historicTaskInstanceList.size() == 0) {
            return;
        }
        //历史实例过程查询
        List<HistoricProcessInstance> historicProcessInstancesList = historyService.createHistoricProcessInstanceQuery()
                .processInstanceIds(historicTaskInstanceList.stream().map(TaskInfo::getProcessInstanceId).collect(Collectors.toSet()))
                .list();

        //获取businessKey
        List<Map> collect = historicProcessInstancesList.stream().map(e -> {
            if (!"entityId".equals(e.getBusinessKey())) {
                return null;
            } else {
                Map result = new LinkedHashMap();
                result.put("BusinessKey", e.getBusinessKey());
                result.put("Name", e.getName());
                result.put("CreateTime", e.getStartTime());
                result.put("TaskId", e.getId());
                System.out.println(result);
                return result;
            }
        }).collect(Collectors.toList());
        collect.removeAll(Collections.singleton(null));
        System.out.println(collect.size());
    }
}
