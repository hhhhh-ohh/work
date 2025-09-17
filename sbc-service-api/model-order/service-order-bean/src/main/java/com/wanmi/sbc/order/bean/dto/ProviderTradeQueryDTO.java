package com.wanmi.sbc.order.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.ChannelType;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.OrderType;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.vo.TradeGrouponVO;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.enums.PaymentOrder;
import com.wanmi.sbc.order.bean.enums.QueryOrderType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @Author: qiaokang
 * @Description:
 * @Date: 2020-03-27 13:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ProviderTradeQueryDTO extends BaseQueryRequest {

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String id;

    /**
     * 客户名称-模糊查询
     */
    @Schema(description = "客户名称")
    private String buyerName;

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    private String buyerId;

    /**
     * 客户账号-模糊查询
     */
    @Schema(description = "客户账号 模糊查询")
    private String buyerAccount;

    /**
     * 商品名称-模糊查询
     */
    @Schema(description = "商品名称 模糊查询")
    private String skuName;

    @Schema(description = "商品编号 模糊查询")
    private String skuNo;

    /**
     * 收货人-模糊查询
     */
    @Schema(description = "收货人 模糊查询")
    private String consigneeName;

    @Schema(description = "收货人联系方式 模糊查询")
    private String consigneePhone;

    /**
     * 订单状态-精确查询
     */
    @Schema(description = "订单状态 精确查询")
    private TradeStateDTO tradeState;

    /**
     * 用于根据ids批量查询场景
     */
    @Schema(description = "用于根据ids批量查询场景")
    private String[] ids;

    /**
     * 退单创建开始时间，精确到天
     */
    @Schema(description = "退单创建开始时间,精确到天")
    private String beginTime;

    /**
     * 退单创建结束时间，精确到天
     */
    @Schema(description = "退单创建结束时间,精确到天")
    private String endTime;

    /**
     * 客户端条件搜索时使用，订单编号或商品名称
     */
    @Schema(description = "客户端条件搜索时使用,订单编号或商品名称")
    private String keyworks;

    /**
     * 商家id-精确查询
     */
    @Schema(description = "商家id 精确查询")
    private Long supplierId;

    /**
     * 商家编码-模糊查询
     */
    @Schema(description = "商家编码 模糊查询")
    private String supplierCode;

    /**
     * 商家名称-模糊查询
     */
    @Schema(description = "商家名称 模糊查询")
    private String supplierName;

    /**
     * 代销商家类型：supplierCode--商家编号、supplierName---商家名称
     */
    @Schema(description = "代销商家类型：supplierCode--商家编号、supplierName---商家名称")
    private String supplierOptions;

    /**
     * 代销商家类型查询值
     */
    @Schema(description = "代销商家类型查询值")
    private String supplierOptionsValue;

    /**
     * 店铺Id
     */
    @Schema(description = "店铺编码 精确查询")
    private Long storeId;

    /**
     * 已完成订单允许申请退单时间
     */
    @Schema(description = "已完成订单允许申请退单时间")
    private Integer day;

    /**
     * 是否允许退单
     */
    @Schema(description = "是否允许退单",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private Integer status;

    /**
     * 业务员id
     */
    @Schema(description = "业务员id")
    private String employeeId;

    /**
     * 业务员id集合
     */
    @Schema(description = "业务员id集合")
    private List<String> employeeIds;


    /**
     * 客户id
     */
    @Schema(description = "客户id")
    private Object[] customerIds;

    /**
     * 是否boss或商家端
     */
    @Schema(description = "是否boss或商家端",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private Boolean isBoss;

    /**
     * 批量流程状态
     */
    @Schema(description = "批量流程状态")
    private List<FlowState> flowStates;

    /**
     * 批量非流程状态
     */
    @Schema(description = "批量非流程状态")
    private List<FlowState> notFlowStates;

    /**
     * 订单支付顺序
     */
    @Schema(description = "订单支付顺序")
    private PaymentOrder paymentOrder;

    /**
     * 开始支付时间
     */
    @Schema(description = "开始支付时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startPayTime;

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
     * 是否拼团订单
     */
    @Schema(description = "是否拼团订单")
    private Boolean grouponFlag;

    /**
     * 订单拼团信息
     */
    private TradeGrouponVO tradeGroupon;

    /**
     * 订单完成开始时间
     */
    private String completionBeginTime;

    /**
     * 订单完成结束时间
     */
    private String completionEndTime;

    /**
     * 筛选第三方平台订单
     */
    @Schema(description = "筛选第三方平台订单")
    private ThirdPlatformType thirdPlatformType;

    /**
     * 筛选第三方平台订单
     */
    @Schema(description = "渠道订单待处理")
    private Boolean thirdPlatformToDo;

    /**
     * 发货状态
     */
    @Schema(description = "发货状态")
    private String deliverStatus;

    /**
     * 筛选订单类型
     */
    @Schema(description = "筛选订单类型")
    private QueryOrderType queryOrderType;

    /**
     * 周期购订单计划开始时间
     */
    @Schema(description = "周期购订单计划开始时间")
    private String buyCyclePlanBeginDate;

    /**
     * 周期购订单计划结束时间
     */
    @Schema(description = "周期购订单计划结束时间")
    private String buyCyclePlanEndDate;

    @Schema(description = "订单是否有售后")
    private DefaultFlag hasReturn ;


    /**
     * @return
     */
    public Boolean getIsBoss() {
        if (this.isBoss == null) {
            return Boolean.TRUE;
        }
        return this.isBoss;
    }

    /**
     * 设定状态条件逻辑
     * 已审核状态需筛选出已审核与部分发货
     */
    public void makeAllAuditFlow() {
        if (Objects.nonNull(tradeState) && tradeState.getFlowState() == FlowState.AUDIT) {
            //待发货状态需筛选出未发货与部分发货
            tradeState.setFlowState(null);
            flowStates = Arrays.asList(FlowState.AUDIT, FlowState.DELIVERED_PART);
        }
    }

    /**
     * 已支付状态下-已审核、部分发货订单
     */
    public void setPayedAndAudit() {
        if (Objects.nonNull(tradeState) && tradeState.getFlowState() == FlowState.AUDIT) {
            //待发货状态需筛选出未发货与部分发货
            tradeState.setPayState(PayState.PAID);
            tradeState.setFlowState(null);
            flowStates = Arrays.asList(FlowState.AUDIT, FlowState.DELIVERED_PART);
        }
    }


    @Override
    public String getSortColumn() {
        return "tradeState.createTime";
    }

    @Override
    public String getSortRole() {
        return "desc";
    }

    @Override
    public String getSortType() {
        return "Date";
    }
}
