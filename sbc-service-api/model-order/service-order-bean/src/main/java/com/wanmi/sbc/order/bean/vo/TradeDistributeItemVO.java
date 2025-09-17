package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午3:40 2019/3/4
 * @Description: 下单分销单品信息
 */
@Data
public class TradeDistributeItemVO extends BasicResponse {

    /**
     * 单品id
     */
    private String goodsInfoId;

    /**
     * 购买数量
     */
    private Long num;

    /**
     * 总sku的实付金额
     */
    private BigDecimal actualPaidPrice;

    /**
     * 佣金比例
     */
    private BigDecimal commissionRate;

    /**
     * 总佣金
     */
    private BigDecimal commission;

    /**
     * 总佣金(返利人佣金 + 提成人佣金)
     */
    @Schema(description = "总佣金(返利人佣金 + 提成人佣金)")
    private BigDecimal totalCommission;

    /**
     * 分销单品佣金提成信息列表
     */
    private List<TradeDistributeItemCommissionVO> commissions;

}
