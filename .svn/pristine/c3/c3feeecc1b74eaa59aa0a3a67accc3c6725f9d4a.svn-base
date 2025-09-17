package com.wanmi.sbc.vas.api.request.recommend.intelligentrecommendation;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 订单商品数据埋单
 * @author  lvzhenwei
 * @date 2021/4/16 1:44 下午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntelligentRecommendationClickGoodsOrderRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /** 订单商品数据 */
    @Schema(description = "订单商品数据")
    private List<String> goodsIds;

    /** 客户id */
    @Schema(description = "客户id")
    private String customerId;

    /** 订单id */
    @Schema(description = "订单id")
    private String orderId;

    /** 订单类型，0：立即购买下单；1：普通下单 */
    @Schema(description = "订单类型")
    private Integer orderType;

    /** 终端 */
    @Schema(description = "终端类型，PC：1，H5：2， APP：3，小程序：4")
    private Integer terminalType;

    /** 事件类型 0：浏览，1：点击，2：加购，3：下单 */
    @Schema(description = "事件类型 0：浏览，1：点击，2：加购，3：下单")
    private Integer eventType;
}
