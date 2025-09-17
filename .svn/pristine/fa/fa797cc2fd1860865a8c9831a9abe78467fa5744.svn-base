package com.wanmi.sbc.order.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.DeliverWay;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.marketing.bean.enums.CommunitySalesType;
import com.wanmi.sbc.marketing.bean.vo.TradeGrouponVO;
import com.wanmi.sbc.order.bean.enums.*;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.enums.QueryPayType;
import com.wanmi.sbc.order.bean.vo.CreditPayInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-04 13:47
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeQueryDTO extends BaseQueryRequest {

    /**
     * 主订单编号
     */
    @Schema(description = "主订单编号")
    private String id;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private List<String> tradeIds;

    /**
     * 子订单编号
     */
    @Schema(description = "子订单编号")
    private String providerTradeId;
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
     * 供应商-模糊查询
     */
    @Schema(description = "供应商编号 模糊查询")
    private String providerCode;

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
     * 店铺Id
     */
    @Schema(description = "店铺编码 精确查询")
    private Long storeId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称 模糊查询")
    private String storeName;

    /**
     * 已完成订单允许申请退单时间
     */
    @Schema(description = "已完成订单允许申请退单时间")
    private Integer day;

    /**
     * 是否允许退单
     */
    @Schema(description = "是否允许退单", contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
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
    @Schema(description = "是否boss或商家端", contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private Boolean isBoss;

    /**
     * 是否团长中心
     */
    @Schema(description = "是否团长中心", contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private Boolean isLeaderCenter;

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
     * 供应商名称
     */
    @Schema(description = "供应商名称")
    private String providerName;

    /**
     * 筛选订单类型
     */
    @Schema(description = "筛选订单类型")
    private QueryOrderType queryOrderType;

    /**
     * 筛选支付方式
     */
    @Schema(description = "筛选支付方式")
    private QueryPayType queryPayType;

    /**
     * 支付方式
     */
    @Schema(description = "支付方式，0：在线支付 1：线下支付")
    private PayType payType;

    /**
     * 支付渠道
     */
    @Schema(description = "支付渠道，0：微信 1：支付宝 5：企业银联 6：云闪付 8：余额 9：授信 10：拉卡拉")
    private PayWay payWay;

    /**
     * 抵扣方式
     */
    @Schema(description = "支付渠道，0：积分 1：礼品卡")
    private OrderDeductionType orderDeductionType;

    /**
     * 是否是预售商品
     */
    private Boolean isBookingSaleGoods;

    /**
     * 预售类型
     */
    private BookingType bookingType;

    /**
     * 尾款订单号
     */
    private String tailOrderNo;

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
     * 预售尾款到达时间
     */
    @Schema(description = "预售尾款到达时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime bookingTailTime;

    /**
     * 授信支付信息
     */
    @Schema(description = "授信支付信息")
    private CreditPayInfoVO creditPayInfo;

    /**
     * 需要授信还款
     */
    @Schema(description = "需要授信还款")
    private Boolean needCreditRepayFlag;

    /**
     * 展示清关/自发货订单
     */
    @Schema(description = "展示清关/自发货订单")
    private Boolean crossOrderTypeFlag;

    /**
     * 是否跨境订单
     */
    @Schema(description = "是否跨境订单")
    private Boolean crossBorderFlag = false;

    /**
     * 是否跨境订单
     */
    @Schema(description = "拓展属性")
    private Object extensions;

    /**
     * 配送方式
     */
    @Schema(description = "配送方式 0其他 1快递 2同城 3自提")
    private DeliverWay deliverWay;

    /**
     * 店铺类型
     */
    @Schema(description = "店铺类型 0：供应商，1：商家，2：o2o直营店")
    private StoreType storeType;

    /**
     * 是否自提订单
     */
    @Schema(description = "是否自提订单")
    private Boolean pickupFlag;

    /**
     * 自提点所属商家
     */
    @Schema(description = "自提点所属商家")
    private Long pickupStoreId;

    /**
     * 关联自提点
     */
    @Schema(description = "关联自提点")
    private List<Long> pickupIds;

    /**
     * 未绑定员工自提点
     */
    @Schema(description = "未绑定员工自提点")
    private List<Long> noEmployeePickupIds;

    /**
     * 是否允许在途退货
     */
    @Schema(description = "是否允许在途退货")
    private Boolean canOnTheWayReturn;

    /**
     * 商品类型，0:实体商品，1：虚拟商品 2：电子卡券
     */
    @Schema(description = "商品类型")
    private Integer goodsType;

    /***
     * 同城配送收货码
     */
    @Schema(description = "收货码")
    private String orderFinishCode;
    /**
     * 筛选第三方平台订单
     */
    @Schema(description = "筛选代销平台订单")
    private SellPlatformType sellPlatformType;

    /**
     * 带货视频号
     */
    @Schema(description = "带货视频号")
    private String videoName;

    /**
     * 场景值:全部、直播间（下单场景值1176、1177）、橱窗（场景值1195）、视频号活动（场景值1191）、视频号商店（场景值1175）
     */
    @Schema(description = "场景值:全部、直播间（下单场景值1176、1177）、橱窗（场景值1195）、视频号活动（场景值1191）、视频号商店（场景值1175）")
    private Integer sceneGroup;

    @Schema(description = "是否隐藏代销订单")
    private Boolean screenSellPlatform = Boolean.FALSE;

    /**
     * 付费会员id
     */
    @Schema(description = "付费会员id")
    private Integer payMemberLevelId;

    /**
     * 尾款开始支付
     */
    @Schema(description = "尾款开始支付")
    private Boolean bookingStartSendFlag;

    /**
     * 尾款支付超时提醒
     */
    @Schema(description = "尾款支付超时提醒")
    private Boolean bookingEndSendFlag;

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

    /**
     * 是否过滤周期购订单
     */
    @Schema(description = "是否过滤周期购订单")
    private Boolean filterCycleOrder;

    @Schema(description = "订单是否有售后")
    private DefaultFlag hasReturn ;

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
     * 社区团长ID
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
     * 批量-社区团购活动ID
     */
    @Schema(description = "批量-社区团购活动ID")
    private List<String> communityActivityIds;

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

    /**
     * 填充团长名称
     */
    @Schema(description = "填充团长名称 true:填充")
    private Boolean fillLeaderNameFlag;

    /**
     * 需要自定义排序 true:需要
     */
    @Schema(description = "需要自定义排序 true:需要")
    private Boolean customSortFlag;

    /**
     * 团长佣金明细导出
     */
    @Schema(description = "团长佣金明细导出")
    private Boolean leaderCommissionDetailExport;

    /**
     * 付款时间开始时间
     */
    @Schema(description = "付款时间开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime payTimeBegin;

    @Schema(description = "物流单号；发货单/退货单")
    private String deliverNo;

    /**
     * 是否运营端
     */
    public Boolean getIsBoss() {
        return this.isBoss == null ? Boolean.TRUE : this.isBoss;
    }

    /**
     * 是否运营端
     */
    public Boolean getIsNotBoss() {
        return !getIsBoss();
    }

    /**
     * 设定状态条件逻辑
     * 已审核状态需筛选出已审核与部分发货
     */
    public void makeAllAuditFlow() {
        if (Objects.nonNull(tradeState) && tradeState.getFlowState() == FlowState.AUDIT) {
            // 待发货状态需筛选出未发货与部分发货
            tradeState.setFlowState(null);
            flowStates = Arrays.asList(FlowState.AUDIT, FlowState.DELIVERED_PART);
        }
    }

    /**
     * 设定状态条件逻辑
     * 已审核状态需筛选出已审核与部分发货
     */
    public void makeCustomerAllAuditFlow() {
        if (Objects.nonNull(tradeState) && tradeState.getFlowState() == FlowState.AUDIT) {
            //待发货状态需筛选出未发货与部分发货
            tradeState.setFlowState(null);
            flowStates = Arrays.asList(FlowState.AUDIT, FlowState.DELIVERED_PART, FlowState.GROUPON);
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
        String sortColumn = super.getSortColumn();
        if(Boolean.TRUE.equals(customSortFlag) && StringUtils.isNotBlank(sortColumn)) {
            return sortColumn;
        }
        return "tradeState.createTime";
    }

    @Override
    public String getSortRole() {
        String sortRole = super.getSortRole();
        if(Boolean.TRUE.equals(customSortFlag) && StringUtils.isNotBlank(sortRole)) {
            return sortRole;
        }
        return "desc";
    }

    @Override
    public String getSortType() {
        return "Date";
    }

    @Serial
    private static final long serialVersionUID = -735386486841367427L;
}
