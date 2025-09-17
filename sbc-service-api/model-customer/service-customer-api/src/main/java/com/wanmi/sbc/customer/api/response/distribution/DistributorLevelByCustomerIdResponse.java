package com.wanmi.sbc.customer.api.response.distribution;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.DistributorLevelVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributorLevelByCustomerIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 分销员等级信息
     */
    @Schema(description = "分销员等级信息")
    private DistributorLevelVO distributorLevelVO;
}
