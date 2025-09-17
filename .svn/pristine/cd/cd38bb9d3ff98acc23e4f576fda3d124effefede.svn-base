package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.ChannelType;
import com.wanmi.sbc.common.enums.OrderType;
import com.wanmi.sbc.common.enums.SellPlatformType;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.marketing.bean.enums.CommunitySalesType;
import com.wanmi.sbc.order.bean.enums.DeliverStatus;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.enums.TimeState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-12-11
 */
@Data
@Schema
public class TradePageQueryRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 3106550472717776197L;

    /**
     * 订单流程状态
     */
    @Schema(description = "订单流程状态")
    private FlowState flowState;

    /**
     * 订单支付状态
     */
    @Schema(description = "订单支付状态")
    private PayState payState;

    /**
     * 订单发货状态
     */
    @Schema(description = "订单发货状态")
    private DeliverStatus deliverStatus;

    /**
     * 下单时间上限，精度到天
     */
    @Schema(description = "下单时间上限，精度到天")
    private String createdFrom;

    /**
     * 下单时间下限,精度到天
     */
    @Schema(description = "下单时间下限,精度到天")
    private String createdTo;

    /**
     * 关键字，用于搜索订单编号或商品名称
     */
    @Schema(description = "关键字，用于搜索订单编号或商品名称")
    private String keywords;

    /**
     * 邀请人id
     */
    @Schema(description = "邀请人id，用于查询从店铺精选下的单")
    private String inviteeId;

    /**
     * 分销渠道类型
     */
    @Schema(description = "分销渠道类型")
    private ChannelType channelType;

    /**
     * 小b端我的客户列表是否是查询全部
     */
    @Schema(description = "小b端我的客户列表是否是查询全部")
    private boolean customerOrderListAllType;

    /**
     * 订单类型 0：普通订单；1：积分订单
     */
    @Schema(description = "订单类型")
    private OrderType orderType;

    /**
     * 店铺Id
     */
    @Schema(description = "店铺编码 精确查询")
    private Long storeId;

    /**
     * 时间状态
     */
    @Schema(description = "时间状态")
    private TimeState timeState;

    /**
     * 商品类型，0:实体商品，1：虚拟商品 2：电子卡券
     */
    @Schema(description = "商品类型")
    private Integer goodsType;

    /**
     * 筛选第三方平台订单
     */
    @Schema(description = "筛选代销平台订单")
    private SellPlatformType sellPlatformType;

    /**
     * 是否团长订单 0不是 1是
     */
    @Schema(description = "是否团长订单 0不是 1是")
    private Integer communityOrder;

    /**
     * 是否已入账订单
     */
    @Schema(description = "是否已入账订单 0不是 1是")
    private Integer communityReceived;

    /**
     * 社区团长ID
     */
    @Schema(description = "社区团长ID")
    private String leaderId;

    /**
     * 社区团长会员ID
     */
    @Schema(description = "社区团长会员ID")
    private String leaderCustomerId;

    /**
     * 社区团长账号
     */
    @Schema(description = "社区团长账号")
    private String leaderAccount;

    /**
     * 社区团购活动ID
     */
    @Schema(description = "社区团购活动ID")
    private String communityActivityId;

    /**
     * 社区团购类型
     */
    @Schema(description = "社区团购类型")
    private CommunitySalesType salesType;

    /**
     * 入账开始时间，精度到天
     */
    @Schema(description = "入账开始时间，精度到天")
    private String receivedFrom;

    /**
     * 入账结束时间,精度到天
     */
    @Schema(description = "入账结束时间,精度到天")
    private String receivedTo;
}
