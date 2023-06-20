package cn.wolfcode.business.flowdefine.service;

import java.io.InputStream;
import java.util.List;
import cn.wolfcode.business.flowdefine.domain.BusBpmnInfo;
import cn.wolfcode.business.flowdefine.domain.vo.BusBpmnInfoVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 审核流程定义Service接口
 * 
 * @author wolfcode
 * @date 2023-06-03
 */
public interface IBusBpmnInfoService 
{
    /**
     * 查询审核流程定义
     * 
     * @param id 审核流程定义主键
     * @return 审核流程定义
     */
    public BusBpmnInfo selectBusBpmnInfoById(Long id);

    /**
     * 查询审核流程定义列表
     * 
     * @param busBpmnInfo 审核流程定义
     * @return 审核流程定义集合
     */
    public List<BusBpmnInfo> selectBusBpmnInfoList(BusBpmnInfo busBpmnInfo);

    /**
     * 新增审核流程定义
     * 
     * @param busBpmnInfo 审核流程定义
     * @return 结果
     */
    public int insertBusBpmnInfo(BusBpmnInfo busBpmnInfo);

    /**
     * 修改审核流程定义
     * 
     * @param busBpmnInfo 审核流程定义
     * @return 结果
     */
    public int updateBusBpmnInfo(BusBpmnInfo busBpmnInfo);

    /**
     * 批量删除审核流程定义
     * 
     * @param ids 需要删除的审核流程定义主键集合
     * @return 结果
     */
    public int deleteBusBpmnInfoByIds(Long[] ids);

    /**
     * 删除审核流程定义信息
     * 
     * @param id 审核流程定义主键
     * @return 结果
     */
    public int deleteBusBpmnInfoById(Long id);

    int deploy(MultipartFile file, BusBpmnInfoVO busBpmnInfoVO);

    int cancel(Long id);

    InputStream resources(Long id, String type);
}
