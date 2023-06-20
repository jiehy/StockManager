package cn.wolfcode.business.archives.qo;

import cn.wolfcode.business.archives.domain.BusCustomerArchives;
import cn.wolfcode.common.utils.StringUtils;

public class BusArchivesQueryObject extends BusCustomerArchives {
    private String keyword;

    public String getKeyword() {
        return StringUtils.isEmpty(keyword) ? null : this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
