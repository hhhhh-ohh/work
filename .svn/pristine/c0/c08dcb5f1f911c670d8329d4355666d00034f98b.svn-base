package com.wanmi.sbc.order.api.request.thirdplatformtrade;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.ChannelType;
import com.wanmi.sbc.common.enums.OrderType;
import com.wanmi.sbc.marketing.bean.vo.TradeGrouponVO;
import com.wanmi.sbc.order.bean.dto.TradeStateDTO;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PaymentOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <p>第三方渠道订单查询参数结构</p>
 * Created by of628-wenzhi on 2017-07-18-下午3:25.
 */
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThirdPlatformTradeQueryRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 149142593703964072L;

    /**
     * 订单编号
     */
    private String id;

    /**
     * 父订单s
     */
    private List<String> parentIds;

    /**
     * 父订单
     */
    private String parentId;

    /**
     * 客户名称-模糊查询
     */
    private String buyerName;

    /**
     * 客户名称
     */
    private String buyerId;

    /**
     * 客户账号-模糊查询
     */
    private String buyerAccount;

    /**
     * 商品名称-模糊查询
     */
    private String skuName;
    private String skuNo;

    /**
     * 收货人-模糊查询
     */
    private String consigneeName;
    private String consigneePhone;

    /**
     * 订单状态-精确查询
     */
    private TradeStateDTO tradeState;

    /**
     * 用于根据ids批量查询场景
     */
    private String[] ids;

    /**
     * 退单创建开始时间，精确到天
     */
    private String beginTime;

    /**
     * 退单创建结束时间，精确到天
     */
    private String endTime;

    /**
     * 客户端条件搜索时使用，订单编号或商品名称
     */
    private String keyworks;

    /**
     * 商家id-精确查询
     */
    private Long supplierId;

    /**
     * 商家编码-模糊查询
     */
    private String supplierCode;

    /**
     * 商家名称-模糊查询
     */
    private String supplierName;

    /**
     * 供应商名称-模糊查询
     */
    private String providerName;

    /**
     * 供应商code
     */
    private String providerCode;

    /**
     * 店铺Id
     */
    private Long storeId;
    /**
     * 已完成订单允许申请退单时间
     */
    private Integer day;

    /**
     * 是否允许退单
     */
    private Integer status;

    /**
     * 业务员id
     */
    private String employeeId;

    /**
     * 业务员id集合
     */
    private List<String> employeeIds;

    /**
     * 客户id
     */
    private Object[] customerIds;

    /**
     * 是否boss或商家端
     */
//    @Builder.Default
    private Boolean isBoss;

    /**
     * 批量流程状态
     */
    private List<FlowState> flowStates;

    /**
     * 批量非流程状态
     */
    private List<FlowState> notFlowStates;

    /**
     * 订单支付顺序
     */
    private PaymentOrder paymentOrder;

    /**
     * 开始支付时间
     */
    private LocalDateTime startPayTime;

    /**
     * 邀请人id
     */
    private String inviteeId;

    /**
     * 分销渠道类型
     */
    private ChannelType channelType;


    /**
     * 小b端我的客户列表是否是查询全部
     */
    @Schema(description = "小b端我的客户列表是否是查询全部")
    private boolean customerOrderListAllType;


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
     * 订单类型 0：普通订单；1：积分订单
     */
    @Schema(description = "订单类型")
    private OrderType orderType;

    /**
     * 订单完成开始时间
     */
    private String completionBeginTime;

    /**
     * 订单完成结束时间
     */
    private String completionEndTime;

    /**
     * 支付单ID
     */
    private String payOrderId;

    /**
     *
     * @return
     */
    public Boolean getIsBoss(){
        if(this.isBoss == null){
            return Boolean.TRUE;
        }
        return this.isBoss;
    }

    /**
     * 设定状态条件逻辑
     * 已审核状态需筛选出已审核与部分发货
     */
    public void makeAllAuditFlow(){
        if(Objects.nonNull(tradeState) && tradeState.getFlowState() ==  FlowState.AUDIT){
            //待发货状态需筛选出未发货与部分发货
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
