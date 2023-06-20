package cn.wolfcode.business.report.mapper;

import cn.wolfcode.business.report.controller.ReportController;

import java.util.List;
import java.util.Map;

public interface ReportMapper {
    List<Map<String, Object>> selectReportList(ReportController.BusReportVO busReportVO);

    List<Map<String, Object>> selectCountMoneyList(ReportController.BusReportVO busReportVO);

    List<Map<String, Object>> selectUserConsumptionAmount();

}
