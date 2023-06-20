package cn.wolfcode.business.appointment.service;

import java.util.List;
import cn.wolfcode.business.appointment.domain.BusAppointment;
import cn.wolfcode.business.appointment.domain.vo.BusAppointmentVO;

/**
 * 养修信息预约Service接口
 * 
 * @author ruoyi
 * @date 2023-05-28
 */
public interface IBusAppointmentService 
{
    /**
     * 查询养修信息预约
     * 
     * @param id 养修信息预约主键
     * @return 养修信息预约
     */
    public BusAppointment selectBusAppointmentById(Long id);

    /**
     * 查询养修信息预约列表
     * 
     * @param busAppointment 养修信息预约
     * @return 养修信息预约集合
     */
    public List<BusAppointment> selectBusAppointmentList(BusAppointment busAppointment);

    /**
     * 新增养修信息预约
     * 
     * @param busAppointmentVo 养修信息预约
     * @return 结果
     */
    public int insertBusAppointment(BusAppointmentVO busAppointmentVo);

    /**
     * 修改养修信息预约
     * 
     * @param busAppointmentVo 养修信息预约
     * @return 结果
     */
    public int updateBusAppointment(BusAppointmentVO busAppointmentVo);

    /**
     * 批量删除养修信息预约
     * 
     * @param ids 需要删除的养修信息预约主键集合
     * @return 结果
     */
    public int deleteBusAppointmentByIds(Long[] ids);

    /**
     * 删除养修信息预约信息
     * 
     * @param id 养修信息预约主键
     * @return 结果
     */
    public int deleteBusAppointmentById(Long id);

    int arral(Long id);

    int close(Long id);

    Long settlement(Long id);

    void sync(BusAppointmentVO busAppointmentVO);
}
