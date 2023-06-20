package cn.wolfcode.business.appointment.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import cn.wolfcode.business.appointment.domain.BusStatement;
import cn.wolfcode.business.appointment.domain.vo.BusStatementVO;
import cn.wolfcode.business.appointment.mapper.BusStatementMapper;
import cn.wolfcode.business.appointment.util.RegexUtils;
import cn.wolfcode.business.appointment.domain.vo.BusAppointmentVO;
import cn.wolfcode.business.appointment.util.VehiclePlateNoUtil;
import cn.wolfcode.business.archives.domain.vo.BusCustomerArchivesVO;
import cn.wolfcode.business.archives.service.IBusCustomerArchivesService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.business.appointment.mapper.BusAppointmentMapper;
import cn.wolfcode.business.appointment.domain.BusAppointment;
import cn.wolfcode.business.appointment.service.IBusAppointmentService;
import org.springframework.util.ObjectUtils;

/**
 * 养修信息预约Service业务层处理
 *
 * @author ruoyi
 * @date 2023-05-28
 */
@Service
public class BusAppointmentServiceImpl implements IBusAppointmentService {
    private static final String regExp = new StringBuilder("(")
            .append(BusAppointmentVO.STATUS_ARRIVE).append("|")
            .append(BusAppointmentVO.STATUS_SETTLEMENT).append("|")
            .append(BusAppointmentVO.STATUS_PAY).append(")").toString();

    private static final String STATUS_APPOINTMENT = "状态必须在预约中";

    private static final String STATUS_ILLEGAL = "非法操作";

    @Autowired
    private BusAppointmentMapper busAppointmentMapper;

    @Autowired
    private BusStatementMapper busStatementMapper;

    @Autowired
    private IBusCustomerArchivesService busCustomerFollowUpService;

    /**
     * 查询养修信息预约
     *
     * @param id 养修信息预约主键
     * @return 养修信息预约
     */
    @Override
    public BusAppointment selectBusAppointmentById(Long id) {
        return busAppointmentMapper.selectBusAppointmentById(id);
    }

    /**
     * 查询养修信息预约列表
     *
     * @param busAppointment 养修信息预约
     * @return 养修信息预约
     */
    @Override
    public List<BusAppointment> selectBusAppointmentList(BusAppointment busAppointment) {
        return busAppointmentMapper.selectBusAppointmentList(busAppointment);
    }

    /**
     * 新增养修信息预约
     *
     * @param busAppointmentVo 养修信息预约
     * @return 结果
     */
    @Override
    public int insertBusAppointment(BusAppointmentVO busAppointmentVo) {
        check(busAppointmentVo);

        BusAppointment busAppointment = new BusAppointment();
        BeanUtils.copyProperties(busAppointmentVo, busAppointment);

        busAppointment.setStatus(BusAppointmentVO.STATUS_APPOINTMENT);

        busCustomerFollowUpService.CopyCustomerFollowUpInput(new HashMap<String, Object>() {{
            put("phone",busAppointmentVo.getCustomerPhone());
            put("name",busAppointmentVo.getCustomerName());
            put("carSeries",busAppointmentVo.getCarSeries());
            put("payRecord", BusCustomerArchivesVO.PAY_RECORD_NO);
        }});

        return busAppointmentMapper.insertBusAppointment(busAppointment);
    }

    /**
     * 修改养修信息预约
     *
     * @param busAppointmentVo 养修信息预约
     * @return 结果
     */
    @Override
    public int updateBusAppointment(BusAppointmentVO busAppointmentVo) {
        check(busAppointmentVo);

        BusAppointment bus = checkAppointment(busAppointmentVo.getId());
        flag(BusAppointmentVO.STATUS_APPOINTMENT.equals(bus.getStatus()), STATUS_APPOINTMENT);

        BusAppointment busAppointment = new BusAppointment();
        BeanUtils.copyProperties(busAppointmentVo, busAppointment);

        busCustomerFollowUpService.CopyCustomerFollowUpUpdate(new HashMap<String, Object>() {{
            put("phone",busAppointmentVo.getCustomerPhone());
            put("name",busAppointmentVo.getCustomerName());
            put("carSeries",busAppointmentVo.getCarSeries());
            put("payRecord",BusCustomerArchivesVO.PAY_RECORD_NO);
        }});

        return busAppointmentMapper.updateBusAppointment(busAppointment);
    }

    /**
     * 批量删除养修信息预约
     *
     * @param ids 需要删除的养修信息预约主键
     * @return 结果
     */
    @Override
    public int deleteBusAppointmentByIds(Long[] ids) {
        List<BusAppointment> list = busAppointmentMapper.selectBusAppointmentByIds(ids);
        flag(list.size() == ids.length, "数据错误有预约不存在");
        for (BusAppointment busAppointment : list) {
            flag(!String.valueOf(busAppointment.getStatus()).matches(regExp), "预约不符合删除条件");
        }
        return busAppointmentMapper.deleteBusAppointmentByIds(ids);
    }

    /**
     * 删除养修信息预约信息
     *
     * @param id 养修信息预约主键
     * @return 结果
     */
    @Override
    public int deleteBusAppointmentById(Long id) {
        BusAppointment busAppointment = checkAppointment(id);

        flag(!String.valueOf(busAppointment.getStatus()).matches(regExp), "预约不符合删除条件");

        return busAppointmentMapper.deleteBusAppointmentById(id);
    }

    /**
     * 修改养修信息预约到店
     *
     * @param id 养修信息预约主键
     * @return 结果
     */
    @Override
    public int arral(Long id) {
        BusAppointment busAppointment = checkAppointment(id);
        flag(BusAppointmentVO.STATUS_APPOINTMENT.equals(busAppointment.getStatus()), STATUS_APPOINTMENT);
        return busAppointmentMapper.updateBusAppointmentStatusById(id, BusAppointmentVO.STATUS_ARRIVE);
    }

    /**
     * 修改养修信息预约用户取消
     *
     * @param id 养修信息预约主键
     * @return 结果
     */
    @Override
    public int close(Long id) {
        BusAppointment busAppointment = checkAppointment(id);
        flag(BusAppointmentVO.STATUS_APPOINTMENT.equals(busAppointment.getStatus()), STATUS_APPOINTMENT);
        return busAppointmentMapper.updateBuChangeStatusById(id, BusAppointmentVO.STATUS_CANCEL);
    }

    @Override
    public Long settlement(Long id) {
        BusAppointment busAppointment = checkAppointment(id);
        flag(String.valueOf(busAppointment.getStatus()).matches(regExp), "预约状态不符合");
        BusStatement busStatement = busStatementMapper.selectBusStatementByAppointmentId(id);
        if (ObjectUtils.isEmpty(busStatement)) {
            busStatement = new BusStatement();
            busStatement.setCustomerName(busAppointment.getCustomerName());
            busStatement.setCustomerPhone(busAppointment.getCustomerPhone());
            busStatement.setActualArrivalTime(busAppointment.getActualArrivalTime());
            busStatement.setLicensePlate(busAppointment.getLicensePlate());
            busStatement.setCarSeries(busAppointment.getCarSeries());
            busStatement.setServiceType(busAppointment.getServiceType());
            busStatement.setAppointmentId(id);
            busStatement.setStatus(BusStatementVO.STATUS_NOT_PAY);
            busStatement.setInfo(busAppointment.getInfo());
            busStatementMapper.insertBusStatement(busStatement);
            busAppointmentMapper.updateBuChangeStatusById(id, BusAppointmentVO.STATUS_SETTLEMENT);
        }
        return busStatement.getId();
    }

    @Override
    public void sync(BusAppointmentVO busAppointmentVO) {
        busCustomerFollowUpService.CopyCustomerFollowUpInput(new HashMap<String, Object>() {{
            put("phone",busAppointmentVO.getCustomerPhone());
            put("name",busAppointmentVO.getCustomerName());
            put("carSeries",busAppointmentVO.getCarSeries());
            put("payRecord",BusCustomerArchivesVO.PAY_RECORD_NO);
        }});
    }

    //    @Scheduled(fixedDelay = 3,timeUnit = TimeUnit.HOURS)
    public void timingTask() {
        List<Long> list = busAppointmentMapper.selectBusAppointmentTimeoutList();
        if (ObjectUtils.isEmpty(list)){
            return;
        }
        int i = busAppointmentMapper.updateStatusById(list,BusAppointmentVO.STATUS_TIMEOUT);
        System.out.println(list);
        System.out.println(i);
    }

    private void objectIsEmpty(Object object, String msg) {
        if (ObjectUtils.isEmpty(object)) {
            throw new RuntimeException(msg);
        }
    }

    private void flag(boolean flag, String msg) {
        if (!flag) {
            throw new RuntimeException(msg);
        }
    }

    private void check(BusAppointmentVO busAppointmentVo) {
        objectIsEmpty(busAppointmentVo, STATUS_ILLEGAL);

        flag(RegexUtils.isPhoneLegal(busAppointmentVo.getCustomerPhone()), "手机号码错误");

        flag(busAppointmentVo.getAppointmentTime().after(new Date()), "预约时间不符合");

        objectIsEmpty(VehiclePlateNoUtil.getVehiclePlateNo(busAppointmentVo.getLicensePlate()), "车牌号错误");
    }

    private BusAppointment checkAppointment(Long id) {
        objectIsEmpty(id, STATUS_ILLEGAL);
        BusAppointment busAppointment = busAppointmentMapper.selectBusAppointmentById(id);
        objectIsEmpty(busAppointment, "没有此预约");
        return busAppointment;
    }
}
