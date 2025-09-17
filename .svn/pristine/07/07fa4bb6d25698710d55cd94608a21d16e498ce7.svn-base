package com.wanmi.sbc.goods.api.request.common;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoTradeRequest extends BaseRequest {

    /**
     * 购物车单品ids
     */
    @Schema(description = "购物车单品ids")
    @NotEmpty
    private List<String> goodsInfoIds;

    /**
     * 当前登录用户
     */
    @Schema(description = "当前登录用户")
    private CustomerVO customer;

    private Long storeId;

}
