package com.wanmi.sbc.vas.api.request.recommend.intelligentrecommendation;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.vas.bean.enums.recommen.PositionType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * @description 商品智能推荐-点击商品埋点参数
 * @author lvzhenwei
 * @date 2021/4/9 4:09 下午
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntelligentRecommendationClickGoodsRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /** 商品智能推荐-点击推荐商品 */
    @Schema(description = "商品智能推荐-点击推荐商品id")
    private String goodsId;

    /** 商品智能推荐-类目id */
    @Schema(description = "商品智能推荐-类目id")
    private Long cateId;

    /** 推荐类型 0:热门 1:相关性 2:用户兴趣推荐 */
    @Schema(description = "推荐类型  0:热门 1：相关性 2：用户兴趣推荐")
    private Integer recommendType;

    /** 浏览类目还是商品 类目为0,商品为1 */
    @Schema(description = "浏览类目还是商品 类目为0,商品为1")
    private Integer item = NumberUtils.INTEGER_ONE;

    /** 坑位类型，0：购物车，1：商品详情，2：商品列表；3：个人中心；4：会员中心；5：收藏商品；6：支付成功页；7：分类；8:魔方 */
    @Schema(description = "坑位类型，0：购物车，1：商品详情，2：商品列表；3：个人中心；4：会员中心；5：收藏商品；6：支付成功页；7：分类；8:魔方")
    @NotNull
    private PositionType type;

    /** 坑位（大数据平台用） */
    @Schema(description = "坑位")
    private Integer location;

    /** 客户id */
    @Schema(description = "客户id")
    private String customerId;

    /** 事件类型 0：浏览，1：点击，2：加购，3：下单 */
    @Schema(description = "事件类型 0：浏览，1：点击，2：加购，3：下单")
    private Integer eventType = NumberUtils.INTEGER_ONE;

    /** 发生时间--时间戳 */
    @Schema(description = "发生时间--时间戳")
    private String createTime;

    /** 订单id */
    @Schema(description = "订单id")
    private String orderId;

    /** 订单类型，0：立即购买下单；1：普通下单 */
    @Schema(description = "订单类型")
    private Integer orderType;

    /** 终端 */
    @Schema(description = "终端类型，PC：1，H5：2， APP：3，小程序：4")
    private Integer terminalType;
}
