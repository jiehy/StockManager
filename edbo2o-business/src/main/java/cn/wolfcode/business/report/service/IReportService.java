package cn.wolfcode.business.report.service;

import cn.wolfcode.business.report.controller.ReportController;

import java.util.List;
import java.util.Map;

public interface IReportService {
    List<Map<String,Object>> list(ReportController.BusReportVO busReportVO);

    List<Map<String,Object>> countMoney(ReportController.BusReportVO busReportVO);

    List<Map<String,Object>> userMoney();
}
