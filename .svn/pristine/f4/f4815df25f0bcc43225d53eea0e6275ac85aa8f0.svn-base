package com.wanmi.sbc.order.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.OrderType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayState;

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
 * @ClassName PointsTradeQueryDTO
 * @Description 积分订单分页查询查询条件
 * @Author lvzhenwei
 * @Date 2019/5/10 14:24
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class PointsTradeQueryDTO extends BaseQueryRequest {

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
     * 订单创建开始时间，精确到天
     */
    @Schema(description = "订单创建结束时间,精确到天")
    private String beginTime;

    /**
     * 订单创建结束时间，精确到天
     */
    @Schema(description = "订单创建结束时间,精确到天")
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
    @Schema(description = "是否boss或商家端", contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
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
     * 开始支付时间
     */
    @Schema(description = "开始支付时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startPayTime;

    /**
     * 订单类型 0：普通订单；1：积分订单
     */
    @Schema(description = "订单类型")
    private OrderType orderType;

    /**
     * 子订单编号
     */
    @Schema(description = "子订单编号")
    private String providerTradeId;

    /**
     * 供应商名称
     */
    @Schema(description = "供应商名称")
    private String providerName;

    /**
     * 供应商编码
     */
    @Schema(description = "供应商编码")
    private String providerCode;

    /**
     * 商品类型，0:实体商品，1：虚拟商品 2：电子卡券
     */
    @Schema(description = "商品类型")
    private Integer goodsType;

    /**
     * 订单完成开始时间，精确到天
     */
    @Schema(description = "订单创建结束时间,精确到天")
    private String completionBeginTime;

    /**
     * 订单完成结束时间，精确到天
     */
    @Schema(description = "订单创建结束时间,精确到天")
    private String completionEndTime;

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
