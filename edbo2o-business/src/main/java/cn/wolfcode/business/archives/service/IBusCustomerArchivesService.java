package cn.wolfcode.business.archives.service;

import java.util.List;
import java.util.Map;

import cn.wolfcode.business.archives.domain.BusCustomerArchives;
import cn.wolfcode.business.archives.domain.vo.BusCustomerArchivesVO;


/**
 * 客户档案Service接口
 *
 * @author wolfcode
 * @date 2023-06-09
 */
public interface IBusCustomerArchivesService {
    /**
     * 查询客户档案
     *
     * @param id 客户档案主键
     * @return 客户档案
     */
    public BusCustomerArchives selectBusCustomerFollowUpById(Long id);

    /**
     * 查询客户档案列表
     *
     * @param busCustomerFollowUp 客户档案
     * @return 客户档案集合
     */
    public List<BusCustomerArchives> selectBusCustomerFollowUpList(BusCustomerArchives busCustomerFollowUp);

    public List<BusCustomerArchives> selectBusCustomerFollowUpListExport(BusCustomerArchives busCustomerFollowUp);

    /**
     * 新增客户档案
     *
     * @param busCustomerFollowUpVO 客户档案
     * @return 结果
     */
    public int insertBusCustomerFollowUp(BusCustomerArchivesVO busCustomerFollowUpVO);

    /**
     * 修改客户档案
     *
     * @param busCustomerFollowUpVO 客户档案
     * @return 结果
     */
    public int updateBusCustomerFollowUp(BusCustomerArchivesVO busCustomerFollowUpVO);

    /**
     * 批量删除客户档案
     *
     * @param ids 需要删除的客户档案主键集合
     * @return 结果
     */
    public int deleteBusCustomerFollowUpByIds(Long[] ids);

    /**
     * 删除客户档案信息
     *
     * @param id 客户档案主键
     * @return 结果
     */
    public int deleteBusCustomerFollowUpById(Long id);

    void CopyCustomerFollowUpInput(Map<String, Object> map);

    void CopyCustomerFollowUpUpdate(Map<String, Object> map);

    List<String> enter();
}
