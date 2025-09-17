package com.wanmi.sbc.order.returnorder.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.account.bean.enums.RefundStatus;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.vo.CustomerAccountVO;
import com.wanmi.sbc.customer.bean.vo.VideoUserVO;
import com.wanmi.sbc.order.bean.enums.*;
import com.wanmi.sbc.order.bean.vo.TradeDistributeItemVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.order.returnorder.model.entity.ReturnAddress;
import com.wanmi.sbc.order.returnorder.model.entity.ReturnItem;
import com.wanmi.sbc.order.returnorder.model.value.*;
import com.wanmi.sbc.order.trade.model.entity.CreditPayInfo;
import com.wanmi.sbc.order.trade.model.entity.value.Buyer;
import com.wanmi.sbc.order.trade.model.entity.value.Company;
import com.wanmi.sbc.order.trade.model.entity.value.Consignee;
import com.wanmi.sbc.setting.bean.vo.RefundCauseVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 退货单
 * Created by jinwei on 19/4/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 退单号
     */
    @Id
    private String id;

    /**
     * 子订单
     */
    private TradeVO tradeVO;
    /**
     * 订单编号
     */
    @NotBlank
    private String tid;

    /**
     * 子单编号，crossSubTrade的Id
     */
    private String subTid;

    /**
     * 对应的ProviderTrade的Id
     */
    private String ptid;

    /**
     * 对应的ThirdPlatformTrade的Id
     */
    private String thirdPlatformTradeId;

    private String providerId;

    private String providerName;

    private String providerCode;

    private Long providerCompanyInfoId;

    /**
     * linkedmall退货原因id
     */
    private Long thirdReasonId;

    /**
     * linkedmall原因内容
     */
    private String thirdReasonTips;

    /**
     * linkedmall侧 商家同意退单的留言，一般包含实际退货地址
     */
    private String thirdSellerAgreeMsg;


    /**
     * 尾款退单号
     */
    private String businessTailId;

    /**
     * 客户信息 买家信息
     */
    private Buyer buyer;

    /**
     * 客户账户信息
     */
    private CustomerAccountVO customerAccount;

    /**
     * 商家信息
     */
    private Company company;

    /**
     * 退货原因
     */
    private ReturnReason returnReason;

    /**
     * 退货说明
     */
    private String description;

    /**
     * 支付方式
     */
    private ReturnWay returnWay;

    /**
     * 退单附件
     */
    private List<String> images;

    /**
     * 退货商品信息
     */
    private List<ReturnItem> returnItems;

    /**
     * 退单赠品信息
     */
    private List<ReturnItem> returnGifts = new ArrayList<>();

    /**
     * 退单加价购商品信息
     */
    private List<ReturnItem> returnPreferential = new ArrayList<>();

    /**
     * 退货总金额
     */
    private ReturnPrice returnPrice;

    /**
     * 退积分信息
     */
    private ReturnPoints returnPoints;

    /**
     * 收货人信息
     */
    private Consignee consignee;

    /**
     * 退货物流信息
     */
    private ReturnLogistics returnLogistics;

    /**
     * 退货单状态
     */
    private ReturnFlowState returnFlowState;

    /**
     * 退货日志记录
     */
    private List<ReturnEventLog> returnEventLogs = new ArrayList<>();

    /**
     * 分销渠道类型
     */
    private ChannelType channelType;

    /**
     * 邀请人分销员id
     */
    private String distributorId;

    /**
     * 邀请人会员id
     */
    private String inviteeId;

    /**
     * 小店名称
     */
    private String shopName;

    /**
     * 分销员名称
     */
    private String distributorName;

    /**
     * 分销单品列表
     */
    private List<TradeDistributeItemVO> distributeItems = new ArrayList<>();


    /**
     * 拒绝原因
     */
    private String rejectReason;

    /**
     * 支付方式枚举
     */
    private PayType payType;

    /**
     * 支付渠道枚举
     */
    private PayWay payWay;

    /**
     * 退单类型
     */
    private ReturnType returnType;

    /**
     * 退单来源
     */
    private Platform platform;

    /**
     * 退款单状态
     */
    private RefundStatus refundStatus;

    /**
     * 线下退款账户
     */
    private Long offlineAccountId;


    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 退单完成时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime finishTime;

    /**
     * 是否被结算
     */
    private Boolean hasBeanSettled;

    /**
     * 退款失败原因
     */
    private String refundFailedReason;

    /**
     * 退货赠品
     */
    private Boolean returnGift;

    /**
     * 退货加价购
     */
    private List<String> returnPreferentialIds;

    /**
     * 审核时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime auditTime;


    /**
     * 订单所属第三方平台类型
     */
    private ThirdPlatformType thirdPlatformType;

    /**
     * 订单所属第三方平台的订单id
     */
    private String thirdPlatformOrderId;

    /**
     * 第三方内部卖家名称
     */
    private String thirdSellerName;

    /**
     * 第三方内部卖家编号
     */
    private String thirdSellerId;

    /**
     * 第三方平台支付失败状态  true:失败 false:成功
     */
    private Boolean thirdPlatformPayErrorFlag;

    /**
     * 外部商家订单号
     * linkedMall -> 淘宝订单号
     */
    private String outOrderId;

    /**
     * 终端来源
     */
    @Enumerated
    private TerminalSource terminalSource;

    /**
     * 退货地址
     */
    private ReturnAddress returnAddress;

    /**
     * 授信支付信息
     */
    private CreditPayInfo creditPayInfo;
    /**
     * 跨境退单 0 是普通退单 1是跨境退单
     */
    @Schema(description = "跨境退单：0 是普通退单 1是跨境退单")
    private ReturnOrderType returnOrderType = ReturnOrderType.GENERAL_TRADE;


    /**
     * 货物状态 0 未收到货 1 已收到货
     */
    private GoodsInfoState goodsInfoState;

    /**
     * 退货原因 （新）
     */
    private RefundCauseVO refundCause;

    /**
     * 退单的卖家备注
     */
    @Schema(description = "卖家备注")
    private String sellerRemark;

    /**
     * 退单标记
     */
    @Schema(description = "退单标记")
    private ReturnTag returnTag;

    /**
     * 代销平台标识
     */
    @Schema(description = "代销平台标识")
    private SellPlatformType sellPlatformType;

    @Schema(description = "代销平台退单号")
    private String sellPlatformReturnId;

    /**
     * 带货视频号
     */
    @Schema(description = "带货视频号")
    private VideoUserVO videoUser;

    /**
     * 场景值:全部、直播间（下单场景值1176、1177）、橱窗（场景值1195）、视频号活动（场景值1191）、视频号商店（场景值1175）
     */
    @Schema(description = "场景值:全部、直播间（下单场景值1176、1177）、橱窗（场景值1195）、视频号活动（场景值1191）、视频号商店（场景值1175）")
    private Integer sceneGroup;

    @Schema(description = "活动ID")
    private Long marketingId;

    /**
     * 增加操作日志
     *
     * @param log
     * @return
     */
    public List<ReturnEventLog> appendReturnEventLog(ReturnEventLog log) {
        if (returnEventLogs == null) {
            returnEventLogs = new ArrayList<>();
        }
        returnEventLogs.add(0, log);
        return returnEventLogs;
    }

    /**
     * 对比
     *
     * @param returnOrder
     * @return
     */
    public DiffResult<ReturnOrder> diff(ReturnOrder returnOrder) {
        return new DiffBuilder<>(this, returnOrder, ToStringStyle.JSON_STYLE)
                .append("refundCause", refundCause, returnOrder.getRefundCause())
                .append("returnReason", returnReason, returnOrder.getReturnReason())
                .append("returnWay", returnWay, returnOrder.getReturnWay())
                .append("returnPrice", returnPrice, returnOrder.getReturnPrice())
                .append("returnPoints", returnPoints, returnOrder.getReturnPoints())
                .append("returnItems", returnItems, returnOrder.getReturnItems())
                .append("description", description, returnOrder.getDescription())
                .append("images", images, returnOrder.getImages())
                .build();
    }

    public void merge(ReturnOrder newReturnOrder) {
        DiffResult<ReturnOrder> diffResult = this.diff(newReturnOrder);
        diffResult.getDiffs().forEach(
                diff -> {
                    String fieldName = diff.getFieldName();
                    switch (fieldName) {
                        case "refundCause":
                            mergeSimple(fieldName, diff.getRight());
                            break;
                        case "returnReason":
                            mergeSimple(fieldName, diff.getRight());
                            break;
                        case "returnWay":
                            mergeSimple(fieldName, diff.getRight());
                            break;
                        case "description":
                            mergeSimple(fieldName, diff.getRight());
                            break;
                        case "images":
                            mergeSimple(fieldName, diff.getRight());
                            break;
                        case "returnPrice":
                            returnPrice.merge(newReturnOrder.getReturnPrice());
                            break;
                        case "returnPoints":
                            returnPoints.merge(newReturnOrder.getReturnPoints());
                            break;
                        case "returnItems":
                            if (!CollectionUtils.isEmpty(newReturnOrder.getReturnItems())) {
                                Map<String, ReturnItem> returnItemMap = newReturnOrder.getReturnItems().stream().collect(Collectors.toMap(
                                        ReturnItem::getSkuId, Function.identity()
                                ));
                                returnItems.stream().forEach(
                                        returnItem -> returnItem.merge(returnItemMap.get(returnItem.getSkuId()))
                                );
                            }
                            break;
                        default:
                            break;
                    }
                }
        );
    }


    /**
     * 拼接所有diff
     *
     * @param returnOrder
     * @return
     */
    public List<String> buildDiffStr(ReturnOrder returnOrder) {
        DiffResult<ReturnOrder> diffResult = this.diff(returnOrder);
        return buildDiffStr(diffResult, returnOrder).stream().filter(diff -> Objects.nonNull(diff) && StringUtils.isNotBlank(diff)).collect(Collectors.toList());
    }

    public List<String> buildDiffStr(DiffResult<ReturnOrder> diffResult, ReturnOrder returnOrder) {
        Function<Object, String> f = (s) -> {
            if (s == null || StringUtils.isBlank(s.toString())) {
                return "空";
            } else {
                return s.toString().trim();
            }
        };
        return diffResult.getDiffs().stream().map(
                diff -> {
                    if (!(
                            (diff.getLeft() == null || StringUtils.isBlank(diff.getLeft().toString()))
                                    &&
                                    (diff.getRight() == null || StringUtils.isBlank(diff.getRight().toString()))
                    )) {
                        String result = null;
                        switch (diff.getFieldName()) {
                            case "refundCause":
                                String refundCause;
                                if (Objects.isNull(diff.getLeft())) {
                                    refundCause = this.getReturnReason().getDesc();
                                } else {
                                    refundCause = ((RefundCauseVO) diff.getLeft()).getCause();
                                }
                                result = String.format("退货原因由 %s 变更为 %s",
                                        f.apply(refundCause),
                                        f.apply(((RefundCauseVO) diff.getRight()).getCause())
                                );
                                break;
//                            case "returnReason":
//                                String returnReason;
//                                if (Objects.isNull(diff.getRight())) {
//                                    returnReason = this.getRefundCause().getCause();
//                                } else {
//                                    returnReason = ((ReturnReason) diff.getRight()).getDesc();
//                                }
//                                result = String.format("退货原因由 %s 变更为 %s",
//                                        f.apply(((ReturnReason) diff.getLeft()).getDesc()),
//                                        f.apply(returnReason)
//                                );
//                                break;
                            case "description":
                                result = String.format("退货说明由 %s 变更为 %s",
                                        f.apply(diff.getLeft()),
                                        f.apply(diff.getRight())
                                );
                                break;
                            case "returnWay":
                                result = String.format("退货方式由 %s 变更为 %s",
                                        f.apply(((ReturnWay) diff.getLeft()).getDesc()),
                                        f.apply(((ReturnWay) diff.getRight()).getDesc())
                                );
                                break;

                            case "returnPrice":
                                result = StringUtils.join(returnPrice.buildDiffStr(returnOrder.getReturnPrice())
                                                .stream().filter(StringUtils::isNotBlank).collect(Collectors.toList())
                                        , ",");
                                break;

                            case "returnPoints":
                                result = StringUtils.join(returnPoints.buildDiffStr(returnOrder.getReturnPoints())
                                                .stream().filter(StringUtils::isNotBlank).collect(Collectors.toList())
                                        , ",");
                                break;

                            case "returnItems":
                                if (!CollectionUtils.isEmpty(returnItems) && !CollectionUtils.isEmpty(returnOrder.getReturnItems())) {
                                    Map<String, ReturnItem> oldMap = returnItems.stream().collect(Collectors.toMap(ReturnItem::getSkuId, Function.identity()));
                                    List<String> results = returnOrder.getReturnItems().stream().map(
                                            t -> StringUtils.join(oldMap.get(t.getSkuId()).buildDiffStr(t), ",")
                                    ).filter(StringUtils::isNotBlank).collect(Collectors.toList());
                                    if (!results.isEmpty()) {
                                        result = StringUtils.join(results, ",");
                                    }
                                }
                                break;
                            case "images":
                                result = "修改退单附件";
                                break;
                            default:
                                break;
                        }
                        return result;
                    }
                    return null;
                }
        ).collect(Collectors.toList());
    }

    private void mergeSimple(String fieldName, Object right) {
        Field field = ReflectionUtils.findField(ReturnOrder.class, fieldName);
        try {
            field.setAccessible(true);
            field.set(this, right);
        } catch (IllegalAccessException e) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050023, new Object[]{ReturnOrder.class, fieldName});
        }
    }

}
