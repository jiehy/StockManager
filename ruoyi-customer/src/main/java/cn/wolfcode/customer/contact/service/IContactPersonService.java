package cn.wolfcode.customer.contact.service;

import cn.wolfcode.customer.contact.domain.vo.ContactPersonVO;
import cn.wolfcode.customer.contact.domain.ContactPerson;

import java.util.List;

/**
 * 客户联系人Service接口
 *
 * @author HH
 * @date 2023-06-16
 */
public interface IContactPersonService {
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
    public int insertContactPerson(ContactPersonVO contactPerson);

    /**
     * 修改客户联系人
     *
     * @param contactPerson 客户联系人
     * @return 结果
     */
    public int updateContactPerson(ContactPersonVO contactPerson);

    /**
     * 批量删除客户联系人
     *
     * @param ids 需要删除的客户联系人主键集合
     * @return 结果
     */
    public int deleteContactPersonByIds(Long[] ids);

    /**
     * 删除客户联系人信息
     *
     * @param id 客户联系人主键
     * @return 结果
     */
    public int deleteContactPersonById(Long id);

    List<String> selectCustomerListName(String customerName);
}
