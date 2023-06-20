package cn.wolfcode.customer.contact.mapper;

import cn.wolfcode.customer.contact.domain.ContactPerson;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户联系人Mapper接口
 *
 * @author HH
 * @date 2023-06-16
 */
public interface ContactPersonMapper {
    /**
     * 查询客户联系人
     *
     * @param id 客户联系人主键
     * @return 客户联系人
     */
    public ContactPerson selectContactPersonById(Long id);

    /**
     * 查询客户联系人列表
     *
     * @param contactPerson 客户联系人
     * @return 客户联系人集合
     */
    public List<ContactPerson> selectContactPersonList(ContactPerson contactPerson);

    /**
     * 新增客户联系人
     *
     * @param contactPerson 客户联系人
     * @return 结果
     */
    public int insertContactPerson(ContactPerson contactPerson);

    /**
     * 修改客户联系人
     *
     * @param contactPerson 客户联系人
     * @return 结果
     */
    public int updateContactPerson(ContactPerson contactPerson);

    /**
     * 删除客户联系人
     *
     * @param id 客户联系人主键
     * @return 结果
     */
    public int deleteContactPersonById(Long id);

    /**
     * 批量删除客户联系人
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteContactPersonByIds(Long[] ids);

    List<String> selectCustomerListName(@Param("customerName") String customerName);
}
