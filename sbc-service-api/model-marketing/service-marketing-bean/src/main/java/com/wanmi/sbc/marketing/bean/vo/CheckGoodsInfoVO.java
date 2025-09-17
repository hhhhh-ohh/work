package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: gaomuwei
 * @Date: Created In 上午10:31 2019/1/9
 * @Description:
 */
@Schema
@Data
public class CheckGoodsInfoVO extends BasicResponse {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private String goodsInfoId;

    /**
     * 均摊价
     */
    @Schema(description = "均摊价")
    private BigDecimal splitPrice;

    @Schema(description = "礼品卡活动Id")
    private Long preferentialMarketingId;

}
