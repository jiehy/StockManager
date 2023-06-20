package cn.wolfcode.business.flowdefine.domain.vo;

import cn.wolfcode.business.appointment.util.CheckAnnotations;

public class BusBpmnInfoVO
{
    public static final Long PACKAGE_TYPE = 2L;

    @CheckAnnotations(message = "流程类型不能为空")
    private Integer bpmnType;
    private String bpmnInfo;
    private String bpmnLabel;

    public Integer getBpmnType() {
        return bpmnType;
    }

    public void setBpmnType(String bpmnType) {
        if ("null".equals(bpmnType)) {
            this.bpmnType = null;
            return;
        }
        this.bpmnType = Integer.valueOf(bpmnType);
    }

    public String getBpmnLabel() {
        return bpmnLabel;
    }

    public void setBpmnLabel(String bpmnLabel) {
        this.bpmnLabel = bpmnLabel;
    }

    public String getBpmnInfo() {
        return bpmnInfo;
    }

    public void setBpmnInfo(String bpmnInfo) {
        this.bpmnInfo = bpmnInfo;
    }
}
