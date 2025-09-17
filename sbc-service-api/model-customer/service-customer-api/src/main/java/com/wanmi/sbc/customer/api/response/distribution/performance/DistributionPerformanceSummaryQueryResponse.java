package com.wanmi.sbc.customer.api.response.distribution.performance;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.DistributionPerformanceSummaryVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>查询指定分销员指定日期范围内汇总业绩数据response</p>
 * Created by of628-wenzhi on 2019-04-23-18:32.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistributionPerformanceSummaryQueryResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 分销员业绩月汇总数据
     */
    @Schema(description = "分销员业绩月汇总数据")
    private List<DistributionPerformanceSummaryVO> dataList;
}
