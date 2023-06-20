package cn.wolfcode.business.flowdefine.service.impl;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import cn.wolfcode.business.appointment.domain.vo.BusServiceItemVO;
import cn.wolfcode.business.appointment.mapper.BusServiceItemMapper;
import cn.wolfcode.business.flowdefine.domain.vo.BusHistoryAuditVO;
import cn.wolfcode.business.flowdefine.domain.vo.BusPackageReviewVO;
import cn.wolfcode.common.utils.DateUtils;
import cn.wolfcode.common.utils.SecurityUtils;
import cn.wolfcode.common.utils.StringUtils;
import org.activiti.bpmn.model.*;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.business.flowdefine.mapper.BusCarPackageAuditMapper;
import cn.wolfcode.business.flowdefine.domain.BusCarPackageAudit;
import cn.wolfcode.business.flowdefine.service.IBusCarPackageAuditService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * 套餐审核Service业务层处理
 *
 * @author wolfcode
 * @date 2023-06-04
 */
@Service
public class BusCarPackageAuditServiceImpl implements IBusCarPackageAuditService {
    private static List<BusCarPackageAudit> audits = Collections.emptyList();

    @Autowired
    private BusCarPackageAuditMapper busCarPackageAuditMapper;

    @Autowired
    private BusServiceItemMapper busServiceItemMapper;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RuntimeService runtimeService;


    /**
     * 查询套餐审核
     *
     * @param id 套餐审核主键
     * @return 套餐审核
     */
    @Override
    public BusCarPackageAudit selectBusCarPackageAuditById(Long id) {
        return busCarPackageAuditMapper.selectBusCarPackageAuditById(id);
    }

    /**
     * 查询套餐审核列表
     *
     * @param busCarPackageAudit 套餐审核
     * @return 套餐审核
     */
    @Override
    public List<BusCarPackageAudit> selectBusCarPackageAuditList(BusCarPackageAudit busCarPackageAudit) {
        return busCarPackageAuditMapper.selectBusCarPackageAuditList(busCarPackageAudit);
    }

    /**
     * 新增套餐审核
     *
     * @param busCarPackageAudit 套餐审核
     * @return 结果
     */
    @Override
    public int insertBusCarPackageAudit(BusCarPackageAudit busCarPackageAudit) {
        busCarPackageAudit.setCreateTime(DateUtils.getNowDate());
        return busCarPackageAuditMapper.insertBusCarPackageAudit(busCarPackageAudit);
    }

    /**
     * 修改套餐审核
     *
     * @param busCarPackageAudit 套餐审核
     * @return 结果
     */
    @Override
    public int updateBusCarPackageAudit(BusCarPackageAudit busCarPackageAudit) {
        return busCarPackageAuditMapper.updateBusCarPackageAudit(busCarPackageAudit);
    }

    /**
     * 批量删除套餐审核
     *
     * @param ids 需要删除的套餐审核主键
     * @return 结果
     */
    @Override
    public int deleteBusCarPackageAuditByIds(Long[] ids) {
        return busCarPackageAuditMapper.deleteBusCarPackageAuditByIds(ids);
    }

    /**
     * 删除套餐审核信息
     *
     * @param id 套餐审核主键
     * @return 结果
     */
    @Override
    public int deleteBusCarPackageAuditById(Long id) {
        return busCarPackageAuditMapper.deleteBusCarPackageAuditById(id);
    }

    @Override
    public InputStream progressAudit(Long id) {
        BusCarPackageAudit carPackageAudit = checkAudit(id);

        HistoricProcessInstance result = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(carPackageAudit.getInstanceId()).singleResult();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(result.getProcessDefinitionId());

        List<String> node = new ArrayList<>();
        List<String> line = new ArrayList<>();
        if (!BusCarPackageAudit.STATUS_CANCEL.equals(carPackageAudit.getStatus())) {
            List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(carPackageAudit.getInstanceId())
                    .list();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getEndTime() == null) {
                    break;
                }

                node.add(list.get(i).getActivityId());

                if ("startEvent".equals(list.get(i).getActivityType())) {
                    StartEvent startEvent = (StartEvent) bpmnModel.getFlowElement(list.get(i).getActivityId());
                    addLine(i, line, startEvent.getOutgoingFlows(), list);
                }

                if ("userTask".equals(list.get(i).getActivityType())) {
                    UserTask userTask = (UserTask) bpmnModel.getFlowElement(list.get(i).getActivityId());
                    addLine(i, line, userTask.getOutgoingFlows(), list);
                }

                if ("exclusiveGateway".equals(list.get(i).getActivityType())) {
                    ExclusiveGateway exclusiveGateway = (ExclusiveGateway) bpmnModel.getFlowElement(list.get(i).getActivityId());
                    addLine(i, line, exclusiveGateway.getOutgoingFlows(), list);
                }
            }
        }

        return new DefaultProcessDiagramGenerator().generateDiagram(bpmnModel, node, line
                , "宋体", "宋体", "宋体");
    }

    private static void addLine(int i, List<String> line, List<SequenceFlow> outgoingFlows, List<HistoricActivityInstance> list) {
        int j = i + 1;
        for (SequenceFlow outgoingFlow : outgoingFlows) {
            if (outgoingFlow.getTargetFlowElement().getId().equals(list.get(j).getActivityId())) {
                line.add(outgoingFlow.getId());
                return;
            }
        }
    }

    @Override
    public void test() {
        taskService.complete("35019", new HashMap<String, Object>() {
            {
                put("shopOwner", true);
            }
        });
    }

    @Override
    public List<BusHistoryAuditVO> queryHistoryById(Long id) {
        BusCarPackageAudit carPackageAudit = checkAudit(id);
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(carPackageAudit.getInstanceId())
                .list();
        List<BusHistoryAuditVO> historyAudits = new ArrayList<>();
        if (!ObjectUtils.isEmpty(list)) {
            for (HistoricTaskInstance instance : list) {
                String comMessage = null;
                List<Comment> taskComments = taskService.getTaskComments(instance.getId());
                for (Comment comment : taskComments) {
                    comMessage = new StringBuilder().append(comment.getFullMessage()).toString();
                }

                String datePoor = null;
                if (!ObjectUtils.isEmpty(instance.getEndTime())) {
                    datePoor = DateUtils.getDatePoor(instance.getEndTime(), instance.getStartTime());
                }
                historyAudits.add(new BusHistoryAuditVO(instance.getName()
                        , instance.getStartTime()
                        , instance.getEndTime()
                        , datePoor
                        , comMessage));
            }
        }
        return historyAudits;
    }

    @Override
    @Transactional
    public int cancel(Long id) {
        BusCarPackageAudit carPackageAudit = checkAudit(id);
        Assert.state(BusCarPackageAudit.STATUS_IN_ROGRESS.equals(carPackageAudit.getStatus()), "必须在进行中的状态才能撤销");
        runtimeService.deleteProcessInstance(carPackageAudit.getInstanceId(), "特殊原因需要撤销");
        busServiceItemMapper.changeAuditStatusById(carPackageAudit.getServiceItemId(), BusServiceItemVO.AUDIT_STATUS_INITIALIZE);
        carPackageAudit.setStatus(BusCarPackageAudit.STATUS_CANCEL);
        return busCarPackageAuditMapper.updateBusCarPackageAudit(carPackageAudit);
    }

    @Override
    public List<BusCarPackageAudit> queryTodo(BusCarPackageAudit busCarPackageAudit) {
        List<Task> list = taskService.createTaskQuery().taskAssignee(SecurityUtils.getUserId().toString()).list();
        if (ObjectUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<String> ids = list.stream().map(Task::getProcessInstanceId).collect(Collectors.toList());
        audits = busCarPackageAuditMapper.selectBusCarPackageAuditByInstancedIds(ids, busCarPackageAudit);
        return audits;
    }

    @Override
    @Transactional
    public void packageReview(BusPackageReviewVO packageReviewVO) {
        Assert.notNull(packageReviewVO, "非法操作");
        Assert.notNull(packageReviewVO.getAudit(), "请输入审核结果");
        BusCarPackageAudit carPackageAudit = checkAudit(packageReviewVO.getId());
        Assert.state(BusCarPackageAudit.STATUS_IN_ROGRESS.equals(carPackageAudit.getStatus()), "必须是进行中才能审核");
        Task task = taskService.createTaskQuery().taskAssignee(SecurityUtils.getUserId().toString())
                .processInstanceId(carPackageAudit.getInstanceId())
                .singleResult();
        Assert.notNull(task, "非法操作");
        if (!StringUtils.isEmpty(packageReviewVO.getOpinion())) {
            taskService.addComment(task.getId(), carPackageAudit.getInstanceId(), "[" + SecurityUtils.getUsername() + ":" + packageReviewVO.getOpinion() + "]");
        }
        taskService.complete(task.getId(), new HashMap<String, Object>() {{
            put("shopOwner", packageReviewVO.getAudit());
        }});

        if (packageReviewVO.getAudit()) {
            List<Task> list = taskService.createTaskQuery().processInstanceId(carPackageAudit.getInstanceId()).list();
            if (ObjectUtils.isEmpty(list)) {
                busServiceItemMapper.changeAuditStatusById(carPackageAudit.getServiceItemId(), BusServiceItemVO.AUDIT_STATUS_APPROVED);
                carPackageAudit.setStatus(BusCarPackageAudit.STATUS_PASS);
                busCarPackageAuditMapper.updateBusCarPackageAudit(carPackageAudit);
            }
        }
        if (!packageReviewVO.getAudit()) {
            busServiceItemMapper.changeAuditStatusById(carPackageAudit.getServiceItemId(), BusServiceItemVO.AUDIT_STATUS_REFUSED);
            carPackageAudit.setStatus(BusCarPackageAudit.STATUS_REJECT);
            busCarPackageAuditMapper.updateBusCarPackageAudit(carPackageAudit);
        }
    }

    @Override
    public List<BusCarPackageAudit> queryDone(BusCarPackageAudit busCarPackageAudit) {
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(SecurityUtils.getUserId().toString())
                .finished()
                .list();
        if(ObjectUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<String> ids = list.stream().map(HistoricTaskInstance::getProcessInstanceId).collect(Collectors.toList());
        audits = busCarPackageAuditMapper.selectBusCarPackageAuditByInstancedIds(ids, busCarPackageAudit);
        return audits;
    }

    private BusCarPackageAudit checkAudit(Long id) {
        Assert.notNull(id, "非法操作");
        BusCarPackageAudit carPackageAudit = busCarPackageAuditMapper.selectBusCarPackageAuditById(id);
        Assert.notNull(carPackageAudit, "没有此审核");
        return carPackageAudit;
    }
}
