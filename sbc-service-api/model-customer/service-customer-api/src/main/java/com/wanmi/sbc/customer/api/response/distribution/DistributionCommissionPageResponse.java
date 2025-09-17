package com.wanmi.sbc.customer.api.response.distribution;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.DistributionCommissionForPageVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by feitingting on 2019/2/26.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DistributionCommissionPageResponse extends BasicResponse {
    /**
     * 分销佣金分页
     */
    @Schema(description = "分销佣金分页")
    private List<DistributionCommissionForPageVO> recordList;

    /**
     * 总数
     */
    @Schema(description = "总数")
    private Long total;

    /**
     * 当前页
     */
    @Schema(description = "当前页")
    private Integer currentPage;
}
