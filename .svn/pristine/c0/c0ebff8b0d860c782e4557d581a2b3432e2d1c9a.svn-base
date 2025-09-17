package com.wanmi.ares.report.employee.model.entity;

import com.wanmi.ares.enums.EmployeeClientSort;
import com.wanmi.ares.enums.StoreSelectType;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

/**
 * <p>业务员获客报表查询参数</p>
 * Created by of628-wenzhi on 2017-10-19-下午6:13.
 */
@ToString
@Data
public class EmployeeClientQuery extends EmployeePageQuery {

//    private StoreSelectType storeSelectType;

    public EmployeeClientQuery(EmployeeClientSort sort) {
        switch (sort) {
            case NEWLY_ASC:
                this.setProperty("newly_num");
                this.setDirection(Sort.Direction.ASC);
                break;
            case NEWLY_DESC:
                this.setProperty("newly_num");
                this.setDirection(Sort.Direction.DESC);
                break;
            case TOTAL_ASC:
                this.setProperty("total");
                this.setDirection(Sort.Direction.ASC);
                break;
            case TOTAL_DESC:
            default:
                this.setProperty("total");
                this.setDirection(Sort.Direction.DESC);
                break;
        }
    }

    public String getTable() {
        if (StringUtils.isNotBlank(this.getYearMonth())) {
            return "employee_client_month";
        }
        switch (this.getDateCycle()) {
            case LATEST_7_DAYS:
                return "employee_client_recent_seven";
            case LATEST_30_DAYS:
                return "employee_client_recent_thirty";
            case TODAY:
            case YESTERDAY:
            default:
                return "employee_client";
        }
    }


}
