package cn.wolfcode.business.flowdefine.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import cn.wolfcode.business.flowdefine.domain.vo.BusBpmnInfoVO;
import cn.wolfcode.common.utils.file.FileUploadUtils;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.business.flowdefine.mapper.BusBpmnInfoMapper;
import cn.wolfcode.business.flowdefine.domain.BusBpmnInfo;
import cn.wolfcode.business.flowdefine.service.IBusBpmnInfoService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 审核流程定义Service业务层处理
 *
 * @author wolfcode
 * @date 2023-06-03
 */
@Service
public class BusBpmnInfoServiceImpl implements IBusBpmnInfoService {
    @Autowired
    private BusBpmnInfoMapper busBpmnInfoMapper;

    @Autowired
    private RepositoryService repositoryService;

    /**
     * 查询审核流程定义
     *
     * @param id 审核流程定义主键
     * @return 审核流程定义
     */
    @Override
    public BusBpmnInfo selectBusBpmnInfoById(Long id) {
        return busBpmnInfoMapper.selectBusBpmnInfoById(id);
    }

    /**
     * 查询审核流程定义列表
     *
     * @param busBpmnInfo 审核流程定义
     * @return 审核流程定义
     */
    @Override
    public List<BusBpmnInfo> selectBusBpmnInfoList(BusBpmnInfo busBpmnInfo) {
        return busBpmnInfoMapper.selectBusBpmnInfoList(busBpmnInfo);
    }

    /**
     * 新增审核流程定义
     *
     * @param busBpmnInfo 审核流程定义
     * @return 结果
     */
    @Override
    public int insertBusBpmnInfo(BusBpmnInfo busBpmnInfo) {
        return busBpmnInfoMapper.insertBusBpmnInfo(busBpmnInfo);
    }

    /**
     * 修改审核流程定义
     *
     * @param busBpmnInfo 审核流程定义
     * @return 结果
     */
    @Override
    public int updateBusBpmnInfo(BusBpmnInfo busBpmnInfo) {
        return busBpmnInfoMapper.updateBusBpmnInfo(busBpmnInfo);
    }

    /**
     * 批量删除审核流程定义
     *
     * @param ids 需要删除的审核流程定义主键
     * @return 结果
     */
    @Override
    public int deleteBusBpmnInfoByIds(Long[] ids) {
        return busBpmnInfoMapper.deleteBusBpmnInfoByIds(ids);
    }

    /**
     * 删除审核流程定义信息
     *
     * @param id 审核流程定义主键
     * @return 结果
     */
    @Override
    public int deleteBusBpmnInfoById(Long id) {
        return busBpmnInfoMapper.deleteBusBpmnInfoById(id);
    }

    @Override
    @Transactional
    public int deploy(MultipartFile file, BusBpmnInfoVO busBpmnInfoVO) {
        Assert.notNull(file, "请上传文件");
        String ext = FileUploadUtils.getExtension(file);
        Assert.state("bpmn".equals(ext), "请上传bpmn格式的文件");
        Deployment deploy = null;
        try {
            deploy = repositoryService.createDeployment()
                    .addInputStream(busBpmnInfoVO.getBpmnLabel(), file.getInputStream())
                    .deploy();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("请上传正确的文件");
        }
        ProcessDefinition result = repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).singleResult();

        BusBpmnInfo busBpmnInfo = new BusBpmnInfo();
        busBpmnInfo.setBpmnLabel(busBpmnInfoVO.getBpmnLabel());
        busBpmnInfo.setBpmnType(busBpmnInfoVO.getBpmnType());
        busBpmnInfo.setDeployTime(new Date());
        busBpmnInfo.setInfo(busBpmnInfoVO.getBpmnInfo());
        busBpmnInfo.setProcessDefinitio(result.getKey());
        busBpmnInfo.setVersion((long) result.getVersion());
        return busBpmnInfoMapper.insertBusBpmnInfo(busBpmnInfo);
    }

    @Override
    @Transactional
    public int cancel(Long id) {
        Assert.notNull(id, "非法操作");
        BusBpmnInfo info = busBpmnInfoMapper.selectBusBpmnInfoById(id);
        Assert.notNull(info, "没有此流程定义");
        ProcessDefinition processDefinition = getProcessDefinition(info);
        repositoryService.deleteDeployment(processDefinition.getDeploymentId(), true);

        return busBpmnInfoMapper.deleteBusBpmnInfoById(id);
    }

    @Override
    public InputStream resources(Long id, String type) {
        Assert.notNull(id, "非法操作");
        Assert.hasLength(type, "非法操作");
        BusBpmnInfo info = busBpmnInfoMapper.selectBusBpmnInfoById(id);
        Assert.notNull(info, "没有此流程定义");
        ProcessDefinition result = getProcessDefinition(info);
        InputStream inputStream = null;
        if ("xml".equals(type)) {
            inputStream = repositoryService.getResourceAsStream(result.getDeploymentId(), info.getBpmnLabel());
        }
        if ("img".equals(type)) {
            BpmnModel bpmnModel = repositoryService.getBpmnModel(result.getId());
            inputStream = new DefaultProcessDiagramGenerator()
                    .generateDiagram(bpmnModel, Collections.emptyList(), Collections.emptyList()
                            , "宋体", "宋体", "宋体");
        }

        return inputStream;
    }

    private ProcessDefinition getProcessDefinition(BusBpmnInfo info) {
        return repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(info.getProcessDefinitio())
                .processDefinitionVersion(info.getVersion().intValue()).singleResult();
    }
}
