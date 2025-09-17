package com.wanmi.sbc.elastic.api.response.customer;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.elastic.bean.vo.customer.EsDistributionCustomerVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: HouShuai
 * @date: 2020/12/7 11:23
 * @description: 分销员分页查询
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EsDistributionCustomerPageResponse extends BasicResponse {

    @Schema(description = "分销员列表")
    private MicroServicePage<EsDistributionCustomerVO> distributionCustomerVOPage;
}
