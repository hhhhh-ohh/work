package com.wanmi.sbc.customer.api.response.company;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 公司信息待审核数量响应
 * @Author: daiyitian
 * @Date: Created In 上午11:38 2017/11/14
 * @Description: 公司信息Response
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyWaitCheckCountResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;
    /**
     * 公司信息待审核数量
     */
    @Schema(description = "公司信息待审核数量")
    private Long count;

    /**
     * 供应商待审核数量
     */
    @Schema(description = "供应商待审核数量")
    private Long providerCount;
}
