package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.customer.bean.vo.CustomerSimplifyOrderCommitVO;
import com.wanmi.sbc.order.bean.enums.OrderSource;
import com.wanmi.sbc.order.bean.vo.PointsTradeItemGroupVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.Range;

/**
 * <p>客户端提交积分订单参数结构，包含除商品信息外的其他必要参数</p>
 * Created by yinxianzhi on 2019-05-20-下午3:40.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema
public class PointsTradeCommitRequest extends BaseRequest {

    private static final long serialVersionUID = 4478928439116796662L;

    /**
     * 订单收货地址id，实体商品必传
     */
    @Schema(description = "订单收货地址id")
    private String consigneeId;

    /**
     * 收货地址详细信息(包含省市区)，实体商品必传
     */
    @Schema(description = "收货地址详细信息(包含省市区)")
    private String consigneeAddress;

    /**
     * 收货地址修改时间，可空
     */
    @Schema(description = "收货地址修改时间")
    private String consigneeUpdateTime;

    /**
     * 积分商品id
     */
    @Schema(description = "积分商品id")
    @NotNull
    private String pointsGoodsId;

    /**
     * 购买数量
     */
    @Schema(description = "购买数量")
    @Range(min = 1)
    private long num;

    /**
     * 订单备注
     */
    @Schema(description = "订单备注")
    private String buyerRemark;

    /**
     * 操作人
     */
    @Schema(description = "操作人")
    private Operator operator;

    /**
     * 下单用户
     */
    @Schema(description = "下单用户")
    private CustomerSimplifyOrderCommitVO customer;

    /**
     * 积分订单项数据
     */
    @Schema(description = "积分订单项数据")
    private PointsTradeItemGroupVO pointsTradeItemGroup;

    /**
     * 是否开启第三方平台
     */
    @Schema(description = "是否开启第三方平台")
    private Boolean isOpen;


    /**
     * 商品类型，0:实体商品，1：虚拟商品 2：电子卡券
     */
    @Schema(description = "商品类型，0:实体商品，1：虚拟商品 2：电子卡券")
    private Integer goodsType;


    /**
     * 订单来源--区分h5,pc,app,小程序,代客下单
     */
    @Schema(description = "订单来源")
    private OrderSource orderSource;
}
