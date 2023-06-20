package cn.wolfcode.business.report.service.impl;

import cn.wolfcode.business.report.controller.ReportController;
import cn.wolfcode.business.report.mapper.ReportMapper;
import cn.wolfcode.business.report.service.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements IReportService {
    @Autowired
    private ReportMapper reportMapper;
    @Override
    public List<Map<String, Object>> list(ReportController.BusReportVO busReportVO) {
        return reportMapper.selectReportList(busReportVO);
    }

    @Override
    public List<Map<String, Object>> countMoney(ReportController.BusReportVO busReportVO) {
        return reportMapper.selectCountMoneyList(busReportVO);
    }

    @Override
    public List<Map<String, Object>> userMoney() {
        return reportMapper.selectUserConsumptionAmount();
    }
}
