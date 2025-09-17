package com.wanmi.sbc.order.pointstrade.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.OrderType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.order.bean.dto.TradeStateDTO;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.util.XssUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
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
 * @ClassName PointsTradeQueryRequest
 * @Description 积分订单查询条件Request
 * @Author lvzhenwei
 * @Date 2019/5/10 14:36
 **/
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointsTradeQueryRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 807992350780125492L;

    /**
     * 订单编号
     */
    private String id;

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

    /**
     * 收货人联系方式 模糊查询
     */
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
     * 订单创建开始时间，精确到天
     */
    private String beginTime;

    /**
     * 订单创建结束时间，精确到天
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
     * 业务员id
     */
    private String employeeId;

    /**
     * 业务员id集合
     */
    @Schema(description = "业务员id集合")
    private List<String> employeeIds;

    /**
     * 客户id
     */
    private Object[] customerIds;

    /**
     * 是否boss或商家端
     */
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
     * 开始支付时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startPayTime;

    /**
     * 订单类型
     */
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
     * 封装公共条件
     *
     * @return
     */
    private List<Criteria> getCommonCriteria() {
        List<Criteria> criterias = new ArrayList<>(33);
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

        //积分订单完成日期，大于/等于开始时间
        if (StringUtils.isNotBlank(completionBeginTime)) {
            criterias.add(Criteria.where("tradeState.endTime").gte(DateUtil.parseDay(completionBeginTime)));
            criterias.add(Criteria.where("tradeState.flowState").is(FlowState.COMPLETED.getStateId()));
        }
        //小与传入的结束时间+1天，零点前
        if (StringUtils.isNotBlank(completionEndTime)) {
            criterias.add(Criteria.where("tradeState.endTime").lt(DateUtil.parseDay(completionEndTime).plusDays(1)));
            criterias.add(Criteria.where("tradeState.flowState").is(FlowState.COMPLETED.getStateId()));
        }

        //所属业务员
        if (StringUtils.isNotBlank(employeeId)) {
            criterias.add(Criteria.where("buyer.employeeId").is(employeeId));
        }

        //所属业务员
        if (CollectionUtils.isNotEmpty(employeeIds)) {
            criterias.add(Criteria.where("buyer.employeeId").in(employeeIds));
        }

        //商家ID
        if (Objects.nonNull(supplierId)) {
            criterias.add(Criteria.where("supplier.supplierId").is(supplierId));
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

        // 订单开始支付时间，开始支付的订单，进行锁定，不能进行其他操作，比如未支付超时作废
        if (Objects.nonNull(startPayTime)) {
            criterias.add(new Criteria().orOperator(Criteria.where("tradeState.startPayTime").lt(startPayTime),
                    Criteria.where("tradeState.startPayTime").exists(false)));
        }

        //订单类型
        if (Objects.nonNull(orderType)) {
            if (orderType == OrderType.NORMAL_ORDER) {
                criterias.add(Criteria.where("id").exists(true).orOperator(Criteria.where("orderType").exists(false), Criteria.where("orderType").is(orderType.getOrderTypeId())));
            } else {
                criterias.add(Criteria.where("orderType").is(orderType));
            }
        } else {
            criterias.add(Criteria.where("id").exists(true).orOperator(Criteria.where("orderType").exists(false), Criteria.where("orderType").is(OrderType.NORMAL_ORDER.getOrderTypeId())));
        }

        // 商品类型，0:实体商品，1：虚拟商品 2：电子卡券
        if (Objects.nonNull(goodsType)){
            switch (goodsType) {
                case 0:
                    criterias.add(new Criteria().andOperator(Criteria.where("orderTag.virtualFlag").is(Boolean.FALSE),
                            Criteria.where("orderTag.electronicCouponFlag").is(Boolean.FALSE)));
                    break;
                case 1:
                    criterias.add(Criteria.where("orderTag.virtualFlag").is(Boolean.TRUE));
                    break;
                case 2:
                    criterias.add(Criteria.where("orderTag.electronicCouponFlag").is(Boolean.TRUE));
                    break;
                default:
                    break;
            }
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
