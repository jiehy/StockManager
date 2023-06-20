package cn.wolfcode.business.appointment.service;

import java.util.List;
import java.util.Map;

import cn.wolfcode.business.appointment.domain.BusStatementItem;
import cn.wolfcode.business.appointment.domain.vo.BusStatementItemVO;

/**
 * 结算单明细Service接口
 *
 * @author wolfcode
 * @date 2023-05-31
 */
public interface IBusStatementItemService {
    /**
     * 查询结算单明细
     *
     * @param id 结算单明细主键
     * @return 结算单明细
     */
    public BusStatementItem selectBusStatementItemById(Long id);

    /**
     * 查询结算单明细列表
     *
     * @param busStatementItem 结算单明细
     * @return 结算单明细集合
     */
    public List<BusStatementItem> selectBusStatementItemList(BusStatementItem busStatementItem);

    /**
     * 新增结算单明细
     *
     * @param busStatementItemVO 结算单明细
     * @return 结果
     */
    public int insertBusStatementItem(BusStatementItemVO busStatementItemVO);

    /**
     * 修改结算单明细
     *
     * @param busStatementItem 结算单明细
     * @return 结果
     */
    public int updateBusStatementItem(BusStatementItem busStatementItem);

    /**
     * 批量删除结算单明细
     *
     * @param ids 需要删除的结算单明细主键集合
     * @return 结果
     */
    public int deleteBusStatementItemByIds(Long[] ids);

    /**
     * 删除结算单明细信息
     *
     * @param id 结算单明细主键
     * @return 结果
     */
    public int deleteBusStatementItemById(Long id);

    int pay(Long id);

    Map<String, String> QRCode();
}
