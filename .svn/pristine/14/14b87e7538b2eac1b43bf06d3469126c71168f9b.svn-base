package com.wanmi.sbc.order.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

import java.util.List;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午3:28 2019/3/6
 * @Description:
 */
@Schema
@Data
public class ImmediateBuyRequest extends BaseRequest {

    /**
     * 批发商品
     */
    @Valid
    @NotEmpty
    private List<TradeItemRequest> tradeItemRequests;

    /**
     * 是否开店礼包
     */
    @Schema(description = "是否开店礼包")
    private DefaultFlag storeBagsFlag;

    /**
     * 门店Id
     */
    @Schema(description = "门店Id")
    private Long storeId;

    /** 地域编码-多级中间用|分割 */
    @Schema(description = "地域编码-多级中间用|分割")
    private String addressId;

}
