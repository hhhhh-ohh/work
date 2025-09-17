package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单跟团记录
 * Created by jinwei on 14/3/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class CommunitySimpleTradeVO extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String id;

    /**
     * 会员昵称
     */
    @Schema(description = "会员昵称")
    private String customerName;

    /**
     * 跟团号
     */
    @Schema(description = "跟团号")
    private String communityOrderNo;

    /**
     * 会员头像
     */
    @Schema(description = "会员头像")
    private String customerHead;

    /**
     * 订单商品列表
     */
    @Schema(description = "订单商品列表")
    private List<TradeItemVO> tradeItems = new ArrayList<>();
}
