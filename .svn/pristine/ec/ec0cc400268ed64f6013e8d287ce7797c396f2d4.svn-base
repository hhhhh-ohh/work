package com.wanmi.sbc.marketing.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午1:58 2018/10/8
 * @Description: 订单营销插件请求类
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeMarketingWrapperDTO implements Serializable {

    private static final long serialVersionUID = -6398979465270944912L;

    /**
     * 客户id
     */
    @Schema(description = "客户id")
    String customerId;

    /**
     * 商品列表
     */
    @Schema(description = "营销订单商品信息列表")
    List<TradeItemInfoDTO> tradeItems;

    /**
     * 订单相关营销信息
     */
    @Schema(description = "订单相关营销信息")
    private TradeMarketingDTO tradeMarketingDTO;

    /**
     * 优惠券码id
     */
    @Schema(description = "优惠券码id")
    private String couponCodeId;

    /**
     * 是否强制提交（忽略失效营销）
     */
    @Schema(description = "是否强制提交（忽略失效营销）")
    private boolean forceCommit;
}
