package com.wanmi.sbc.order.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.account.bean.enums.RefundStatus;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.vo.VideoUserVO;
import com.wanmi.sbc.order.bean.enums.*;
import com.wanmi.sbc.order.bean.vo.ReturnItemVO;
import com.wanmi.sbc.order.bean.vo.TradeDistributeItemVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.setting.bean.vo.RefundCauseVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 退货单
 * Created by jinwei on 19/4/2017.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ReturnOrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 退单号
     */
    @Schema(description = "退单号")
    private String id;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号", required = true)
    @NotBlank
    private String tid;

    @Schema(description = "子订单编号")
    private String ptid;

    @Schema(description = "ThirdPlatformTrade的Id")
    private String thirdPlatformTradeId;

    @Schema(description = "供应商ID")
    private String providerId;

    @Schema(description = "供应商名称")
    private String providerName;

    @Schema(description = "供应商编码")
    private String providerCode;

    @Schema(description = "providerCompanyInfoId")
    private Long providerCompanyInfoId;

    /**
     * linkedmall退货原因id
     */
    @Schema(description = "linkedmall退货原因id")
    private Long thirdReasonId;

    /**
     * linkedmall退货原因内容
     */
    @Schema(description = "linkedmall退货原因内容")
    private String thirdReasonTips;

    /**
     * linkedmall侧 商家同意退单的留言，一般包含实际退货地址
     */
    @Schema(description = "linkedmall侧 商家同意退单的留言，一般包含实际退货地址")
    private String thirdSellerAgreeMsg;

    /**
     * 尾款退单号
     */
    @Schema(description = "尾款退单号")
    private String businessTailId;

    /**
     * 客户信息 买家信息
     */
    @Schema(description = "客户信息 买家信息")
    private BuyerDTO buyer;

    /**
     * 客户账户信息
     */
    @Schema(description = "客户账户信息")
    private ReturnCustomerAccountDTO customerAccount;

    /**
     * 商家信息
     */
    @Schema(description = "商家信息")
    private CompanyDTO company;

    /**
     * 退货原因
     */
    @Schema(description = "退货原因")
    private ReturnReason returnReason;

    /**
     * 退货原因（新，可维护）
     */
    @Schema(description = "退货原因（新，可维护）")
    private RefundCauseVO refundCause;

    /**
     * 退货说明
     */
    @Schema(description = "退货说明")
    private String description;

    /**
     * 退货方式
     */
    @Schema(description = "退货方式")
    private ReturnWay returnWay;

    /**
     * 退单附件
     */
    @Schema(description = "退单附件")
    private List<String> images;

    /**
     * 退货商品信息
     */
    @Schema(description = "退货商品信息")
    private List<ReturnItemDTO> returnItems;

    /**
     * 退单赠品信息
     */
    @Schema(description = "退单赠品信息")
    private List<ReturnItemDTO> returnGifts = new ArrayList<>();

    /**
     * 退货总金额
     */
    @Schema(description = "退货总金额")
    private ReturnPriceDTO returnPrice;

    /**
     * 退积分信息
     */
    @Schema(description = "退积分信息")
    private ReturnPointsDTO returnPoints;

    /**
     * 收货人信息
     */
    @Schema(description = "收货人信息")
    private ConsigneeDTO consignee;

    /**
     * 退货物流信息
     */
    @Schema(description = "退货物流信息")
    private ReturnLogisticsDTO returnLogistics;

    /**
     * 退货单状态
     */
    @Schema(description = "退货单状态")
    private ReturnFlowState returnFlowState;

    /**
     * 退货日志记录
     */
    @Schema(description = "退货日志记录")
    private List<ReturnEventLogDTO> returnEventLogs = new ArrayList<>();

    /**
     * 拒绝原因
     */
    @Schema(description = "拒绝原因")
    private String rejectReason;

    /**
     * 支付方式枚举
     */
    @Schema(description = "支付方式枚举")
    private PayType payType;

    /**
     * 支付渠道枚举
     */
    @Schema(description = "支付渠道枚举")
    private PayWay payWay;

    /**
     * 退单类型
     */
    @Schema(description = "退单类型")
    private ReturnType returnType;

    /**
     * 退单来源
     */
    @Schema(description = "退单来源")
    private Platform platform;

    /**
     * 退款单状态
     */
    @Schema(description = "退款单状态")
    private RefundStatus refundStatus;

    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 退单完成时间
     */
    @Schema(description = "退单完成时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime finishTime;

    /**
     * 是否被结算
     */
    @Schema(description = "是否被结算", contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private Boolean hasBeanSettled;

    /**
     * 分销渠道类型
     */
    @Schema(description = "分销渠道类型")
    private ChannelType channelType;

    /**
     * 退款失败原因
     */
    private String refundFailedReason;


    /**
     * 基础分销设置-小店名称
     */
    @Schema(description = "小店名称")
    private String shopName;


    /**
     * 邀请人分销员id
     */
    @Schema(description = "邀请人分销员id")
    private String distributorId;


    /**
     * 邀请人会员id
     */
    @Schema(description = "邀请人会员id")
    private String inviteeId;

    /**
     * 分销员名称
     */
    @Schema(description = "分销员名称")
    private String distributorName;

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
    @Schema(description = "第三方内部卖家编号")
    private String thirdSellerId;

    /**
     * 第三方平台支付失败状态  true:失败 false:成功
     */
    @Schema(description = "第三方平台支付失败状态  true:失败 false:成功")
    private Boolean thirdPlatformPayErrorFlag;

    /**
     * 外部商家订单号
     * linkedMall -> 淘宝订单号
     */
    @Schema(description = "外部商家订单号")
    private String outOrderId;
    /**
     * 终端来源
     */
    @Schema(description = "终端来源")
    private TerminalSource terminalSource;

    /**
     * 分销单品列表
     */
    @Schema(description = "分销单品列表")
    private List<TradeDistributeItemVO> distributeItems = new ArrayList<>();


    /**
     * 退货赠品
     */
    @Schema(description = "退货赠品")
    private Boolean returnGift;

    /**
     * 退货地址
     */
    @Schema(description = "退货地址")
    private ReturnAddressDTO returnAddress;

    /**
     * 跨境退单 0 是普通退单 1是跨境退单
     */
    @Schema(description = "跨境退单：0 是普通退单 1是跨境退单")
    private ReturnOrderType returnOrderType = ReturnOrderType.GENERAL_TRADE;

    /**
     * 货物状态 0 未收到货 1 已收到货
     */
    @Schema(description = "货物状态：0 未收到货 1 已收到货")
    private GoodsInfoState goodsInfoState;

    /**
     * 退单的卖家备注
     */
    @Schema(description = "卖家备注")
    private String sellerRemark;

    /**
     * 退单标记
     */
    @Schema(description = "退单标记")
    private ReturnTagDTO returnTag;

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

    @Schema(description = "加价购商品ID")
    private List<String> returnPreferentialIds;

    /**
     * 退单加价购商品信息
     */
    @Schema(description = "退单加价购商品信息")
    private List<ReturnItemVO> returnPreferential = new ArrayList<>();

    @Schema(description = "活动ID")
    private Long marketingId;

    /**
     * 关联订单
     */
    private TradeVO tradeVO;

}
