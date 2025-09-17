package com.wanmi.sbc.elastic.api.response.customer;

import com.wanmi.sbc.common.base.BasicResponse;

import com.wanmi.sbc.customer.bean.vo.DistributionCustomerVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsDistributionCustomerAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的分销员信息
     */
    @Schema(description = "分销员信息")
    private List<DistributionCustomerVO> distributionCustomerVO;
}