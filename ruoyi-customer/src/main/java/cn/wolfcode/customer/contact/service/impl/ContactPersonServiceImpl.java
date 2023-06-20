package cn.wolfcode.customer.contact.service.impl;

import cn.wolfcode.customer.contact.domain.vo.ContactPersonVO;
import cn.wolfcode.common.utils.SecurityUtils;
import cn.wolfcode.customer.contact.domain.ContactPerson;
import cn.wolfcode.customer.contact.mapper.ContactPersonMapper;
import cn.wolfcode.customer.contact.service.IContactPersonService;
import cn.wolfcode.customer.contact.util.PhoneUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * 客户联系人Service业务层处理
 *
 * @author HH
 * @date 2023-06-16
 */
@Service
public class ContactPersonServiceImpl implements IContactPersonService {
    @Autowired
    private ContactPersonMapper contactPersonMapper;

    /**
     * 查询客户联系人
     *
     * @param id 客户联系人主键
     * @return 客户联系人
     */
    @Override
    public ContactPerson selectContactPersonById(Long id) {
        return contactPersonMapper.selectContactPersonById(id);
    }

    /**
     * 查询客户联系人列表
     *
     * @param contactPerson 客户联系人
     * @return 客户联系人
     */
    @Override
    public List<ContactPerson> selectContactPersonList(ContactPerson contactPerson) {
        return contactPersonMapper.selectContactPersonList(contactPerson);
    }

    /**
     * 新增客户联系人
     *
     * @param contactPersonVO 客户联系人
     * @return 结果
     */
    @Override
    public int insertContactPerson(ContactPersonVO contactPersonVO) {
        //1 参数校验
        check(contactPersonVO);
        //2 将vo赋值给对象
        ContactPerson contactPerson = new ContactPerson();
        BeanUtils.copyProperties(contactPersonVO, contactPerson);
        //3 设置录入人
        contactPerson.setEntryPerson(SecurityUtils.getUsername());
        //4 设置录入时间
        contactPerson.setEntryTime(new Date());
        //5 设置在职状态
        contactPerson.setEmploymentStatus(0L);
        return contactPersonMapper.insertContactPerson(contactPerson);
    }

    private void check(ContactPersonVO contactPersonVO) {
        Assert.notNull(contactPersonVO, "非法操作");
        Assert.notNull(contactPersonVO.getEnterpriseCustomers(), "所属企业不能为空");
        Assert.notNull(contactPersonVO.getName(), "联系人名字不能为空");
        Assert.notNull(contactPersonVO.getSex(), "性别不能为空");
        Assert.notNull(contactPersonVO.getAge(), "年龄不能为空");
        Assert.notNull(contactPersonVO.getPhone(), "电话不能为空");
        Assert.notNull(contactPersonVO.getJob(), "职位不能为空");
        Assert.notNull(contactPersonVO.getDept(), "部门不能为空");
        boolean mobileNumber = PhoneUtil.isMobileNumber(contactPersonVO.getPhone());
        if (mobileNumber == false) {
            throw new RuntimeException("电话号码不符合要求");
        }
        if (contactPersonVO.getAge() > 100) {
            throw new RuntimeException("年龄不符合要求");
        }
        if (contactPersonVO.getName().length() > 30) {
            throw new RuntimeException("字数30以内");
        }
        if (contactPersonVO.getJob().length() > 20) {
            throw new RuntimeException("字数20以内");
        }
        if (contactPersonVO.getDept().length() > 20) {
            throw new RuntimeException("字数20以内");
        }
    }

    /**
     * 修改客户联系人
     *
     * @param contactPersonVO 客户联系人
     * @return 结果
     */
    @Override
    public int updateContactPerson(ContactPersonVO contactPersonVO) {
        //1 参数校验
        check(contactPersonVO);
        //2 根据id 去数据库查询数据
        ContactPerson contactPerson = contactPersonMapper.selectContactPersonById(contactPersonVO.getId());
        //3 将vo赋值给对象
        BeanUtils.copyProperties(contactPersonVO, contactPerson);

        return contactPersonMapper.updateContactPerson(contactPerson);
    }

    /**
     * 批量删除客户联系人
     *
     * @param ids 需要删除的客户联系人主键
     * @return 结果
     */
    @Override
    public int deleteContactPersonByIds(Long[] ids) {
        return contactPersonMapper.deleteContactPersonByIds(ids);
    }

    /**
     * 删除客户联系人信息
     *
     * @param id 客户联系人主键
     * @return 结果
     */
    @Override
    public int deleteContactPersonById(Long id) {
        return contactPersonMapper.deleteContactPersonById(id);
    }

    @Override
    public List<String> selectCustomerListName(String customerName) {
        return contactPersonMapper.selectCustomerListName(customerName);
    }
}
