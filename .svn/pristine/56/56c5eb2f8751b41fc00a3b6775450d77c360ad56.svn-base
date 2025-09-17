package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.FreightTemplateGroupVO;
import com.wanmi.sbc.order.bean.vo.TradeFreightTemplateVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 各店铺运费
 */
@Data
@Schema
public class TradeGetFreightResponse extends BasicResponse {
    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 配送费用，可以从TradePriceInfo获取
     */
    @Schema(description = "配送费用")
    private BigDecimal deliveryPrice;


    /**
     * 商家运费
     */
    @Schema(description = "商家运费")
    private BigDecimal supplierFreight;

    /**
     * 供应商运费
     */
    @Schema(description = "供应商运费")
    private BigDecimal providerFreight;

    @Schema(description = "运费模板信息")
    private List<TradeFreightTemplateVO> freightInfo;

    @Schema(description = "运费模板分组信息 用于单品运费 + 非免邮")
    private List<FreightTemplateGroupVO> groupVO;

}
