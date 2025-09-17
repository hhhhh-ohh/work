package com.wanmi.sbc.order.api.request.purchase;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-12-03
 */
@Data
@Schema
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseGetStoreCouponExistRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "客户信息")
    private CustomerVO customer;

    @Schema(description = "购物车归属id")
    private String inviteeId;

    @Schema(description = "门店Id")
    private Long storeId;

    @Schema(description = "营销类型")
    private PluginType pluginType;
}
