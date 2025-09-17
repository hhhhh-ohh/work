package com.wanmi.ares.report.flow.model.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.ares.enums.SortOrder;
import com.wanmi.ares.enums.StatisticsDataType;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

/**
 * @ClassName FlowDataRequest
 * @description
 * @Author lvzhenwei
 * @Date 2019/8/22 15:24
 **/
@Data
public class FlowDataRequest extends BaseRequest{

    /**
     * 开始时候
     * 20170925
     */
    private LocalDate beginDate;

    /**
     * 结束时间
     * 20170926
     */
    private LocalDate endDate;

    /**
     * 商户id
     */
    private String companyId;

    /**
     * 统计类型：0：今天，1：昨天，2：最近七天；3：最近30天；4：按月统计，5：最近30天按周统计，6：最近6个月按周统计
     */
    private StatisticsDataType flowDataType;

    /**
     * 统计月份
     */
    private String month;

    /**
     * 按周统计，周开始日期
     */
    private LocalDate weekStartDate;

    /**
     * 按周统计，周结束日期
     */
    private LocalDate weekEndDate;

    private int pageNum = 1;

    private int pageSize = 10;

    /**
     * 排序字段 (默认日期排序)
     */
    private String sortName = "date";

    /**
     * 排序: 默认倒序
     */
    private SortOrder sortOrder = SortOrder.ASC;


}
