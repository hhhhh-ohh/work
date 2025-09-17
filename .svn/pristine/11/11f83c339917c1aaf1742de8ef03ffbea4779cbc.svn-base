package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.account.bean.enums.GatherType;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.account.bean.enums.TradeType;
import com.wanmi.sbc.account.bean.vo.SettleTradeVO;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.OrderType;
import com.wanmi.sbc.common.enums.StoreType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author edz
 * @className LakalaSettleTradeVO
 * @description TODO
 * @date 2022/7/20 16:08
 **/
@Data
@Schema
public class LakalaSettleTradeVO extends SettleTradeVO {

    /**
     * 商家编码
     */
    @Schema(description = "商家编码")
    private String supplierCode;

    /**
     * 商家id
     */
    @Schema(description = "商家ID")
    private Long supplierId;

    /**
     * 店铺id
     */
    @Schema(description = "店铺ID")
    private Long storeId;

    @Schema(description = "父订单ID")
    private String parentId;

    @Schema(description = "供应商分账金额")
    private BigDecimal totalProviderPrice;

    @Schema(description = "分销员分账金额")
    private BigDecimal totalCommissionPrice;

    @Schema(description = "平台分账金额")
    private BigDecimal totalPlatformPrice;

    @Schema(description = "分销返利人佣金")
    private BigDecimal commission = BigDecimal.ZERO;

    @Schema(description = "分销提成人佣金")
    private List<TradeCommissionVO> commissions = new ArrayList<>();

    /**
     * 拉卡拉分配的商户号
     */
    @Schema(description = "拉卡拉分配的商户号")
    private String merchantNo;

    /**
     * 终端号
     */
    @Schema(description = "终端号")
    private String termNo;
}
