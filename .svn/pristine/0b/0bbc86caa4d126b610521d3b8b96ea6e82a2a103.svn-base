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
 * 分销员佣金导出结果
 * Created by of2975 on 2019/4/30.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DistributionCommissionExportResponse extends BasicResponse {
    /**
     * 分销佣金导出结果
     */
    @Schema(description = "分销佣金导出结果")
    private List<DistributionCommissionForPageVO> recordList;

}
