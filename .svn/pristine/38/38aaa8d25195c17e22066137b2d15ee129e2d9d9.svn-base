package com.wanmi.sbc.order.api.request.pointstrade;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.ChannelType;
import com.wanmi.sbc.common.enums.OrderType;
import com.wanmi.sbc.order.bean.enums.DeliverStatus;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @ClassName PointsTradePageQueryRequest
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2019/5/21 14:24
 **/
@Data
@Schema
public class PointsTradePageQueryRequest extends BaseQueryRequest {

    private static final long serialVersionUID = -3022462267170780225L;

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
}
