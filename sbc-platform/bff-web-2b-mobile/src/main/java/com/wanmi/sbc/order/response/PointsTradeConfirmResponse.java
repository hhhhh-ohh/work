package com.wanmi.sbc.order.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.OrderTagVO;
import com.wanmi.sbc.order.bean.vo.PointsTradeItemGroupVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: yinxianzhi
 * @Date: Created In 下午5:58 2019/5/20
 * @Description: 积分订单确认返回结构
 */
@Schema
@Data
public class PointsTradeConfirmResponse extends BasicResponse {

    /**
     * 积分订单项
     */
    @Schema(description = "积分订单项")
    private PointsTradeItemGroupVO pointsTradeConfirmItem;

    /**
     * 订单积分数
     */
    @Schema(description = "订单积分数")
    private Long totalPoints;

    /**
     * 订单标识
     */
    @Schema(description = "订单标识")
    private OrderTagVO orderTagVO;
}