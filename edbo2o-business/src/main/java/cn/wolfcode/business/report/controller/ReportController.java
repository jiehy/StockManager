package cn.wolfcode.business.report.controller;


import cn.wolfcode.business.report.service.IReportService;
import cn.wolfcode.common.core.controller.BaseController;
import cn.wolfcode.common.core.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report/shop")
public class ReportController extends BaseController {

    @Autowired
    private IReportService reportService;

    @PreAuthorize("@ss.hasPermi('report:shop:list')")
    @GetMapping
    public TableDataInfo list(BusReportVO busReportVO) {
        startPage();
        return getDataTable(reportService.list(busReportVO));
    }

    public static class BusReportVO {
        private int timeDim;
        private int groupDim;

        public String getExp() {
            switch (this.timeDim) {
                case 1: return "%Y";
                case 2: return "%Y-%m";
                case 3: return "%Y年%v周";
            }
            return "%Y-%m-%d";
        }

        public String getNotNull() {
            return "IS NOT NULL";
        }

        public int getTimeDim() {
            return timeDim;
        }

        public void setTimeDim(int timeDim) {
            this.timeDim = timeDim;
        }

        public Integer getGroupDim() {
            return groupDim;
        }

        public void setGroupDim(Integer groupDim) {
            this.groupDim = groupDim;
        }
    }

    @PreAuthorize("@ss.hasPermi('report:shop:money')")
    @GetMapping("/countMoney")
    public TableDataInfo countMoney(BusReportVO busReportVO) {
        startPage();
        return getDataTable(reportService.countMoney(busReportVO));
    }

    @PreAuthorize("@ss.hasPermi('report:shop:usermoney')")
    @GetMapping("/userMoney")
    public TableDataInfo userMoney() {
        startPage();
        return getDataTable(reportService.userMoney());
    }
}
