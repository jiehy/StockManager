package cn.wolfcode.business.appointment.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wolfcode.business.appointment.domain.BusStatement;
import cn.wolfcode.business.appointment.domain.vo.BusAppointmentVO;
import cn.wolfcode.business.appointment.domain.vo.BusStatementItemVO;
import cn.wolfcode.business.appointment.domain.vo.BusStatementVO;
import cn.wolfcode.business.appointment.mapper.BusAppointmentMapper;
import cn.wolfcode.business.appointment.mapper.BusStatementMapper;
import cn.wolfcode.business.appointment.util.QrCodeGenerator;
import cn.wolfcode.business.archives.domain.vo.BusCustomerArchivesVO;
import cn.wolfcode.business.archives.mapper.BusCustomerArchivesMapper;
import cn.wolfcode.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.business.appointment.mapper.BusStatementItemMapper;
import cn.wolfcode.business.appointment.domain.BusStatementItem;
import cn.wolfcode.business.appointment.service.IBusStatementItemService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * 结算单明细Service业务层处理
 *
 * @author wolfcode
 * @date 2023-05-31
 */
@Service
public class BusStatementItemServiceImpl implements IBusStatementItemService {
    @Autowired
    private BusStatementItemMapper busStatementItemMapper;

    @Autowired
    private BusStatementMapper busStatementMapper;

    @Autowired
    private BusAppointmentMapper busAppointmentMapper;

    @Autowired
    private BusCustomerArchivesMapper busCustomerFollowUpMapper;

    /**
     * 查询结算单明细
     *
     * @param id 结算单明细主键
     * @return 结算单明细
     */
    @Override
    public BusStatementItem selectBusStatementItemById(Long id) {
        return busStatementItemMapper.selectBusStatementItemById(id);
    }

    /**
     * 查询结算单明细列表
     *
     * @param busStatementItem 结算单明细
     * @return 结算单明细
     */
    @Override
    public List<BusStatementItem> selectBusStatementItemList(BusStatementItem busStatementItem) {
        return busStatementItemMapper.selectBusStatementItemList(busStatementItem);
    }

    /**
     * 新增结算单明细
     *
     * @param busStatementItemVO 结算单明细
     * @return 结果
     */
    @Override
    @Transactional
    public int insertBusStatementItem(BusStatementItemVO busStatementItemVO) {
        BusStatement busStatement = busStatementMapper.selectBusStatementById(busStatementItemVO.getStatementId());
        Assert.notNull(busStatement, "没有此结算单");
        Assert.state(BusStatementVO.STATUS_NOT_PAY.equals(busStatement.getStatus()), "必须是消费中才能保存");
        List<BusStatementItem> list = busStatementItemVO.getBusStatementItems();
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalQuantity = BigDecimal.ZERO;
        busStatementItemMapper.deleteBusStatementItemById(busStatementItemVO.getStatementId());
        if (!ObjectUtils.isEmpty(list)) {
            for (BusStatementItem statementItem : list) {
                totalQuantity = totalQuantity.add(new BigDecimal(statementItem.getItemQuantity()));
                totalAmount = totalAmount.add(statementItem.getItemPrice().multiply(new BigDecimal(statementItem.getItemQuantity())));
            }
            busStatementItemMapper.insertBusStatementItem(list);
        }
        Assert.state(totalAmount.compareTo(busStatementItemVO.getDiscountPrice()) >= 0, "折扣价不能大于总金额");
        busStatement.setTotalAmount(totalAmount);
        busStatement.setTotalQuantity(totalQuantity);
        busStatement.setDiscountAmount(busStatementItemVO.getDiscountPrice());
        return busStatementMapper.updateBusStatement(busStatement);
    }

    /**
     * 修改结算单明细
     *
     * @param busStatementItem 结算单明细
     * @return 结果
     */
    @Override
    public int updateBusStatementItem(BusStatementItem busStatementItem) {
        return busStatementItemMapper.updateBusStatementItem(busStatementItem);
    }

    /**
     * 批量删除结算单明细
     *
     * @param ids 需要删除的结算单明细主键
     * @return 结果
     */
    @Override
    public int deleteBusStatementItemByIds(Long[] ids) {
        return busStatementItemMapper.deleteBusStatementItemByIds(ids);
    }

    /**
     * 删除结算单明细信息
     *
     * @param id 结算单明细主键
     * @return 结果
     */
    @Override
    public int deleteBusStatementItemById(Long id) {
        return busStatementItemMapper.deleteBusStatementItemById(id);
    }


    /**
     * 支付结算单明细
     *
     * @param id 结算单id
     * @return 结果
     */
    @Override
    public int pay(Long id) {
        Assert.notNull(id, "非法操作");
        BusStatement busStatement = busStatementMapper.selectBusStatementById(id);
        Assert.notNull(busStatement, "没有此结算单");
        Assert.state(BusStatementVO.STATUS_NOT_PAY.equals(busStatement.getStatus()), "必须在消费中才能支付");
        busStatement.setPayTime(new Date());
        busStatement.setPayeeId(SecurityUtils.getUserId());
        busStatement.setStatus(BusStatementVO.STATUS_HAVE_PAY);
        if (!ObjectUtils.isEmpty(busStatement.getAppointmentId())) {
            busAppointmentMapper.updateBusAppointmentStatusById(busStatement.getAppointmentId(), BusAppointmentVO.STATUS_PAY);
        }

        busCustomerFollowUpMapper.updateBusCustomerFollowUpPayRecordByPhone(busStatement.getCustomerPhone(), BusCustomerArchivesVO.PAY_RECORD_HAVE);

        return busStatementMapper.updateBusStatement(busStatement);
    }

    @Override
    public Map<String, String> QRCode() {
        return QrCodeGenerator.getCode();
    }
}
