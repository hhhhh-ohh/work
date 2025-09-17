package com.wanmi.sbc.customer.api.response.distribution;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.DistributionCustomerShowPhoneVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author houshuai
 * @date 2020/12/9 16:29
 * @description  <p> </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class EsDistributionCustomerAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的分销员信息
     */
    @Schema(description = "分销员信息")
    private DistributionCustomerShowPhoneVO esDistributionCustomerVO;
}
