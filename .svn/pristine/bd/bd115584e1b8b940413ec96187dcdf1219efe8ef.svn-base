package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>按商家，店铺分组的订单商品快照</p>
 * Created by of628-wenzhi on 2017-11-23-下午2:46.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class TradeItemGroupVO extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 订单商品sku
     */
    @Schema(description = "订单商品sku")
    private List<TradeItemVO> tradeItems;

    /**
     * 加价购订单商品sku
     */
    @Schema(description = "加价购订单商品sku")
    private List<TradeItemVO> preferentialTradeItems;

    /**
     * 商家与店铺信息
     */
    @Schema(description = "商家与店铺信息")
    private SupplierVO supplier;

    /**
     * 订单营销信息
     */
    @Schema(description = "订单营销信息")
    private List<TradeItemMarketingVO> tradeMarketingList;

    /**
     * 是否组合套装--原计划是放在TradeItemMarketingVO的
     */
    @Schema(description = "是否组合套装")
    private Boolean suitMarketingFlag;

    /**
     * 开店礼包
     */
    @Schema(description = "开店礼包")
    private DefaultFlag storeBagsFlag = DefaultFlag.NO;

    /**
     * 快照类型--秒杀活动抢购商品订单快照："FLASH_SALE"
     */
    @Schema(description = "快照类型--秒杀活动抢购商品订单快照：FLASH_SALE")
    private String snapshotType;

    /**
     * 下单拼团相关字段
     */
    private TradeGrouponCommitFormVO grouponForm;

    /**
     * 分销佣金总额
     */
    private BigDecimal commission;

    /**
     * 提成人佣金列表
     */
    private List<TradeCommissionVO> commissions = new ArrayList<>();


    /**
     * 订单标记
     */
    private OrderTagVO orderTag;

}
