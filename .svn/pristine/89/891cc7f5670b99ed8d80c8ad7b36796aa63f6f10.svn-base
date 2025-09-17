package com.wanmi.sbc.marketing.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author wur
 * @className GiftCardTransBusinessVO
 * @description 礼品卡交易明细
 * @date 2022/12/8 18:38
 **/
@Schema
@Data
public class GiftCardTransBusinessVO implements Serializable {

    private static final long serialVersionUID = 4043264917929737209L;
    /**
     * 交易金额
     */
    @Schema(description = "交易金额")
    private BigDecimal tradePrice;

    /**
     * 业务Id
     */
    @Schema(description = "业务Id")
    private String businessId;

    /**
     * 业务明细
     */
    @Schema(description = "业务明细")
    private List<GiftCardTransBusinessItemVO> businessItemVOList;

}