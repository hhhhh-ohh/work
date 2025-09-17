package com.wanmi.sbc.order.trade.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.ChannelType;
import com.wanmi.sbc.common.enums.OrderType;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.goods.bean.enums.DeliverWay;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.marketing.bean.vo.TradeGrouponVO;
import com.wanmi.sbc.order.bean.dto.TradeStateDTO;
import com.wanmi.sbc.order.bean.enums.*;
import com.wanmi.sbc.order.bean.vo.CreditPayInfoVO;
import com.wanmi.sbc.order.util.XssUtils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-04 13:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeCriteriaRequest extends BaseQueryRequest {

    private static final long serialVersionUID = -735386486841367427L;
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
     * 已完成订单允许申请退单时间
     */
    @Schema(description = "已完成订单允许申请退单时间")
    private Integer day;

    /**
     * 是否允许退单
     */
    @Schema(description = "是否允许退单",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class )
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
    @Schema(description = "是否boss或商家端",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class )
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
     * 封装公共条件
     *
     * @return
     */
    private List<Criteria> getCommonCriteria() {
        List<Criteria> criterias = new ArrayList<>(49);
        // 订单编号
        if (StringUtils.isNoneBlank(id)) {
            criterias.add(XssUtils.regex("id", id));
        }

        //批量订单编号
        if (Objects.nonNull(ids) && ids.length > 0) {
            criterias.add(Criteria.where("id").in(Arrays.asList(ids)));
        }

        //时间范围，大于开始时间
        if (StringUtils.isNotBlank(beginTime)) {
            criterias.add(Criteria.where("tradeState.createTime").gte(DateUtil.parseDay(beginTime)));
        }

        //小与传入的结束时间+1天，零点前
        if (StringUtils.isNotBlank(endTime)) {
            criterias.add(Criteria.where("tradeState.createTime").lt(DateUtil.parseDay(endTime).plusDays(1)));
        }

        //订单完成日期，大于/等于开始时间
        if (StringUtils.isNotBlank(completionBeginTime)) {
            criterias.add(Criteria.where("tradeState.endTime").gte(DateUtil.parseDay(completionBeginTime)));
        }
        //小与传入的结束时间+1天，零点前
        if (StringUtils.isNotBlank(completionEndTime)) {
            criterias.add(Criteria.where("tradeState.endTime").lt(DateUtil.parseDay(completionEndTime).plusDays(1)));
        }

        //所属业务员
        if (StringUtils.isNotBlank(employeeId)) {
            criterias.add(Criteria.where("buyer.employeeId").is(employeeId));
        }

        //商家ID
        if (Objects.nonNull(supplierId)) {
            criterias.add(Criteria.where("supplier.supplierId").is(supplierId));
        }

        // 尾款订单号
        if (StringUtils.isNotBlank(tailOrderNo)) {
            criterias.add(Criteria.where("tailOrderNo").is(tailOrderNo));
        }

        //邀请人id
        if (Objects.nonNull(inviteeId)) {
            criterias.add(Criteria.where("inviteeId").is(inviteeId));
        }

        //分销渠道类型
        if (Objects.nonNull(channelType)) {
            criterias.add(Criteria.where("channelType").is(channelType.toString()));
        }

        //批量客户
        if (StringUtils.isNotBlank(buyerId)) {
            criterias.add(Criteria.where("buyer.id").is(buyerId));
        }

        //批量客户
        if (Objects.nonNull(customerIds) && customerIds.length > 0) {
            criterias.add(Criteria.where("buyer.id").in(customerIds));
        }

        // 发货状态
        if (Objects.nonNull(tradeState)) {
            // 发货状态
            if (Objects.nonNull(tradeState.getDeliverStatus())) {
                criterias.add(Criteria.where("tradeState.deliverStatus").is(tradeState.getDeliverStatus().getStatusId()));
            }

            // 支付状态
            if (Objects.nonNull(tradeState.getPayState())) {
                criterias.add(Criteria.where("tradeState.payState").is(tradeState.getPayState().getStateId()));
            }

            // 流程状态
            if (Objects.nonNull(tradeState.getFlowState())) {
                criterias.add(Criteria.where("tradeState.flowState").is(tradeState.getFlowState().getStateId()));
            }
        }

        if (Objects.nonNull(isBookingSaleGoods)) {
            criterias.add(Criteria.where("isBookingSaleGoods").is(isBookingSaleGoods));
        }

        if (Objects.nonNull(bookingType)) {
            criterias.add(Criteria.where("bookingType").is(bookingType));
        }

        if (Objects.nonNull(bookingTailTime)) {
            criterias.add(new Criteria().andOperator(Criteria.where("tradeState.tailStartTime").lt(bookingTailTime), Criteria.where("tradeState.tailEndTime").gt(bookingTailTime)));
        }

        //批量流程状态
        if (CollectionUtils.isNotEmpty(flowStates)) {
            criterias.add(Criteria.where("tradeState.flowState").in(flowStates.stream().map(FlowState::getStateId).collect(Collectors.toList())));
        }

        if (StringUtils.isNotBlank(supplierCode)) {
            criterias.add(XssUtils.regex("supplier.supplierCode", supplierCode));
        }

        if (StringUtils.isNotBlank(supplierName)) {
            criterias.add(XssUtils.regex("supplier.supplierName", supplierName));
        }
        //供应商名称 模糊查询
        if(StringUtils.isNoneBlank(providerName)){
            criterias.add(XssUtils.regex("tradeItems.providerName",providerName));
        }
        //供应商编号
        if (StringUtils.isNoneBlank(providerCode)) {
            criterias.add(XssUtils.regex("tradeItems.providerCode",providerCode));
        }

        // 客户名称-模糊查询
        if (StringUtils.isNotBlank(buyerName)) {
            criterias.add(XssUtils.regex("buyer.name", buyerName));
        }

        // 客户账号-模糊查询
        if (StringUtils.isNotBlank(buyerAccount)) {
            criterias.add(XssUtils.regex("buyer.account", buyerAccount));
        }

        // 收货人
        if (StringUtils.isNotBlank(consigneeName)) {
            criterias.add(XssUtils.regex("consignee.name", consigneeName));
        }

        // 收货电话
        if (StringUtils.isNotBlank(consigneePhone)) {
            criterias.add(XssUtils.regex("consignee.phone", consigneePhone));
        }

        // skuName模糊查询
        if (StringUtils.isNotBlank(skuName)) {
            Criteria orCriteria = new Criteria();
            orCriteria.orOperator(
                    XssUtils.regex("tradeItems.skuName", skuName),
                    XssUtils.regex("gifts.skuName", skuName));
            criterias.add(orCriteria);
        }

        // skuNo模糊查询
        if (StringUtils.isNotBlank(skuNo)) {
            Criteria orCriteria = new Criteria();
            orCriteria.orOperator(
                    XssUtils.regex("tradeItems.skuNo", skuNo),
                    XssUtils.regex("gifts.skuNo", skuNo));
            criterias.add(orCriteria);
        }

        //关键字
        if (StringUtils.isNotBlank(keyworks)) {
            Criteria orCriteria = new Criteria();
            orCriteria.orOperator(
                    XssUtils.regex("id", keyworks),
                    XssUtils.regex("tradeItems.skuName", keyworks),
                    XssUtils.regex("gifts.skuName", keyworks));
            criterias.add(orCriteria);
        }

        //批量流程状态
        if (CollectionUtils.isNotEmpty(notFlowStates)) {
            criterias.add(Criteria.where("tradeState.flowState").nin(notFlowStates.stream().map(FlowState::getStateId).collect(Collectors.toList())));
        }

        // 第三方平台订单
        if(Objects.nonNull(thirdPlatformType)) {
            criterias.add(Criteria.where("thirdPlatformType").is(thirdPlatformType));
        }

        // 订单支付顺序
        if (Objects.nonNull(paymentOrder)) {
            criterias.add(Criteria.where("paymentOrder").is(paymentOrder.getStateId()));
        }

        // 订单开始支付时间，开始支付的订单，进行锁定，不能进行其他操作，比如未支付超时作废
        if (Objects.nonNull(startPayTime)) {
            criterias.add(new Criteria().orOperator(Criteria.where("tradeState.startPayTime").lt(startPayTime),
                    Criteria.where("tradeState.startPayTime").exists(false)));
        }
        // 是否拼团订单
        if (Objects.nonNull(grouponFlag)) {
            criterias.add(Criteria.where("grouponFlag").is(grouponFlag));
        }
        if (Objects.nonNull(tradeGroupon)) {
            // 是否团长订单
            if (Objects.nonNull(tradeGroupon.getLeader())) {
                criterias.add(Criteria.where("tradeGroupon.leader").is(tradeGroupon.getLeader()));
            }

            // 团订单状态
            if (Objects.nonNull(tradeGroupon.getGrouponOrderStatus())) {
                criterias.add(Criteria.where("tradeGroupon.grouponOrderStatus").is(tradeGroupon.getGrouponOrderStatus().toString()));
            }

            // 团编号
            if (Objects.nonNull(tradeGroupon.getGrouponNo())) {
                criterias.add(Criteria.where("tradeGroupon.grouponNo").is(tradeGroupon.getGrouponNo()));
            }
        }


        //订单类型
        if (Objects.nonNull(orderType)) {
            if (orderType == OrderType.NORMAL_ORDER) {
                criterias.add(Criteria.where("id").exists(true).orOperator(Criteria.where("orderType").exists(false), Criteria.where("orderType").is(orderType.getOrderTypeId())));
            } else {
                criterias.add(Criteria.where("orderType").is(orderType));
            }
        } else {
            criterias.add(Criteria.where("id").exists(true).orOperator(Criteria.where("orderType").exists(false), Criteria.where("orderType").is(OrderType.NORMAL_ORDER)));
        }

        //配送方式
        if (Objects.nonNull(deliverWay)) {
            criterias.add(Criteria.where("deliverWay").is(deliverWay));
        }

        //店铺类型
        if (Objects.nonNull(storeType)) {
            criterias.add(Criteria.where("supplier.storeType").is(storeType));
        }
        //自提订单
        if(Objects.nonNull(pickupFlag)){
            criterias.add(Criteria.where("pickupFlag").is(pickupFlag));
        }
        if(Objects.nonNull(pickupStoreId)){
            criterias.add(Criteria.where("pickSettingInfo.storeId").is(pickupStoreId));
        }
        if (CollectionUtils.isNotEmpty(pickupIds) && Objects.isNull(pickupFlag)){
            criterias.add(new Criteria().orOperator(Criteria.where("pickupFlag").exists(false),Criteria.where("pickSettingInfo.pickupId").in(pickupIds)));
        }
        if (CollectionUtils.isNotEmpty(pickupIds) && Boolean.TRUE.equals(pickupFlag)){
            criterias.add(Criteria.where("pickSettingInfo.pickupId").in(pickupIds));
        }
        if (CollectionUtils.isNotEmpty(noEmployeePickupIds) && Objects.isNull(pickupFlag)){
            criterias.add(new Criteria().orOperator(Criteria.where("pickupFlag").exists(false),Criteria.where("pickSettingInfo.pickupId").in(noEmployeePickupIds)));
        }
        if (CollectionUtils.isNotEmpty(noEmployeePickupIds) && Boolean.TRUE.equals(pickupFlag)){
            criterias.add(Criteria.where("pickSettingInfo.pickupId").in(noEmployeePickupIds));
        }

        return criterias;
    }

    /**
     * 公共条件
     *
     * @return
     */
    public Criteria getWhereCriteria() {
        List<Criteria> criteriaList = this.getCommonCriteria();
        if (CollectionUtils.isEmpty(criteriaList)) {
            return new Criteria();
        }
        return new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()]));
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
