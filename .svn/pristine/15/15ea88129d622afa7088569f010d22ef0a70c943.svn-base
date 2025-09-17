package com.wanmi.sbc.marketing.api.request.gift;

import com.wanmi.sbc.customer.bean.dto.CustomerDTO;
import com.wanmi.sbc.marketing.api.request.market.MarketingIdRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-20 16:44
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullGiftLevelListByMarketingIdAndCustomerRequest extends MarketingIdRequest {

    private static final long serialVersionUID = 5217744673798119535L;
//    /**
//     * 客户信息
//     */
//    @Schema(description = "客户信息")
//    private CustomerDTO customer;

    @Schema(description = "客户id")
    private String customerId;

    @Schema(description = "门店id")
    private Long storeId;

    /**
     * 是否营销查询商品
     */
    @Schema(description = "是否营销查询商品")
    private Boolean isMarketing = false;
}
