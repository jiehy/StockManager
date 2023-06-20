package cn.wolfcode.business.appointment.mapper;

import java.util.List;
import cn.wolfcode.business.appointment.domain.BusAppointment;
import org.apache.ibatis.annotations.*;

/**
 * 养修信息预约Mapper接口
 * 
 * @author ruoyi
 * @date 2023-05-28
 */
public interface BusAppointmentMapper 
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
//    @Results({
//            @Result(id = true,property = ""),
//            @Result(),})
//    @Select("select id, customer_name, customer_phone, appointment_time, actual_arrival_time, license_plate, car_series, service_type, create_time, info, status, isdelete from bus_appointment")
    public List<BusAppointment> selectBusAppointmentList(BusAppointment busAppointment);

    /**
     * 新增养修信息预约
     * 
     * @param busAppointment 养修信息预约
     * @return 结果
     */
    public int insertBusAppointment(BusAppointment busAppointment);

    /**
     * 修改养修信息预约
     * 
     * @param busAppointment 养修信息预约
     * @return 结果
     */
    public int updateBusAppointment(BusAppointment busAppointment);

    /**
     * 删除养修信息预约
     * 
     * @param id 养修信息预约主键
     * @return 结果
     */
    public int deleteBusAppointmentById(Long id);

    /**
     * 批量删除养修信息预约
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBusAppointmentByIds(Long[] ids);

    int updateBusAppointmentStatusById(@Param("id") Long id, @Param("statusArrive") Integer statusArrive);

    List<BusAppointment> selectBusAppointmentByIds(@Param("ids") Long[] ids);

    int updateBuChangeStatusById(@Param("id") Long id, @Param("statusCancel") Integer statusCancel);

    List<Long> selectBusAppointmentTimeoutList();

    int updateStatusById(@Param("list") List<Long> list, @Param("statusCancel") Integer statusCancel);
}
