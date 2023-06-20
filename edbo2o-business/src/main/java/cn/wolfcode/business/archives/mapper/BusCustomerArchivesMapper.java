package cn.wolfcode.business.archives.mapper;

import java.util.List;

import cn.wolfcode.business.archives.domain.BusCustomerArchives;
import org.apache.ibatis.annotations.Param;

/**
 * 客户档案Mapper接口
 *
 * @author wolfcode
 * @date 2023-06-09
 */
public interface BusCustomerArchivesMapper {
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
     * @param busCustomerArchives 客户档案
     * @return 客户档案集合
     */
    public List<BusCustomerArchives> selectBusCustomerFollowUpList(BusCustomerArchives busCustomerArchives);

    public List<BusCustomerArchives> selectBusCustomerFollowUpListExport(BusCustomerArchives busCustomerArchives);

    /**
     * 新增客户档案
     *
     * @param busCustomerFollowUp 客户档案
     * @return 结果
     */
    public int insertBusCustomerFollowUp(BusCustomerArchives busCustomerFollowUp);

    /**
     * 修改客户档案
     *
     * @param busCustomerFollowUp 客户档案
     * @return 结果
     */
    public int updateBusCustomerFollowUp(BusCustomerArchives busCustomerFollowUp);

    /**
     * 删除客户档案
     *
     * @param id 客户档案主键
     * @return 结果
     */
    public int deleteBusCustomerFollowUpById(Long id);

    /**
     * 批量删除客户档案
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBusCustomerFollowUpByIds(Long[] ids);

    Long selectBusCustomerFollowUpByPhone(String phone);

    void updateBusCustomerFollowUpPayRecordByPhone(@Param("customerPhone") String customerPhone, @Param("payRecordHave") Integer payRecordHave);

    List<String> selectBusCustomerFollowUpEnterList();
}
