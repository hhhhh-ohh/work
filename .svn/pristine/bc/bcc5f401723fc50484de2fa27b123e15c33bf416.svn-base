package com.wanmi.ares.report.employee.model.entity;

import com.wanmi.ares.enums.QueryDateCycle;
import com.wanmi.ares.enums.StoreSelectType;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>业务员报表分页排序查询参数</p>
 * Created by of628-wenzhi on 2017-10-25-下午6:29.
 */
@Data
@NoArgsConstructor
public class EmployeePageQuery {
    /**
     * 商户id
     */
    private String companyId;

    /**
     * 业务员id集合
     */
    private List<String> employeeIds;

    /**
     * 年月周期信息
     */
    private String yearMonth;

    /**
     * 日期周期信息
     */
    private QueryDateCycle dateCycle;

    /**
     * 分页开始index
     */
    private int pageBegin;

    /**
     * 页数
     */
    private int pageSize;

    /**
     * 排序规则
     */
    private Sort.Direction direction;

    /**
     * 排序字段
     */
    private String property;

    /**
     * 0全部，1商家，2门店
     */
    private Integer storeSelectType;

    public void setStoreSelectType(StoreSelectType storeSelectType) {
        this.storeSelectType = storeSelectType == null ? 0 : storeSelectType.toValue();
    }

    //天报表查询时使用
    public String getDay() {
        switch (this.getDateCycle()) {
            case TODAY:
                return LocalDate.now().toString();
            case YESTERDAY:
                return LocalDate.now().minusDays(1).toString();
            default:
                return LocalDate.now().toString();
        }

    }
}
