package com.wanmi.sbc.elastic.api.response.customer;

import com.wanmi.sbc.common.base.BasicResponse;

import com.wanmi.sbc.customer.bean.vo.DistributionCustomerShowPhoneVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author houshuai
 * @date 2020/12/9 17:45
 * @description <p> 分销员列表返回结果 </p>
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EsDistributionCustomerListResponse extends BasicResponse {

    @Schema(description = "分销员列表")
    private List<DistributionCustomerShowPhoneVO> list;
}
