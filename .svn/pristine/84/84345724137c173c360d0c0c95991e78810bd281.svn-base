package com.wanmi.sbc.order.api.optimization.trade1.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.DistributeChannel;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.marketing.bean.dto.TradeMarketingDTO;
import com.wanmi.sbc.order.bean.enums.BuyType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author edz
 * @className ImmediateBuyRequest
 * @description TODO
 * @date 2022/2/23 15:28
 **/
@Data
@Schema
public class TradeBuyRequest extends BaseRequest {

    @Schema(description = "购买商品信息")
    private List<TradeItemRequest> tradeItemRequests;

    @Schema(description = "是否开店礼包")
    private DefaultFlag storeBagsFlag;

    @Schema(description = "门店Id")
    private Long storeId;

    @Schema(description = "地域编码-多级中间用|分割")
    private String addressId;

    @Schema(description = "收货地址区的地址码")
    public String areaId;

    @Schema(description = "会员ID")
    private String customerId;

    @Schema(description = "用户是否是企业会员")
    private Boolean iepCustomerFlag;

    @Schema(description = "用户终端的token")
    private String terminalToken;

    @Schema(description = "下单类型")
    @NotNull
    private BuyType buyType;

    @Schema(description = "组合购营销ID")
    private Long marketingId;

    @Schema(description = "渠道信息")
    private DistributeChannel distributeChannel;

    @Schema(description = "营销快照")
    private List<TradeMarketingDTO> tradeMarketingList;

    @Schema(description = "是否强制确认，用于营销活动有效性校验,true: 无效依然提交， false: 无效做异常返回")
    private Boolean forceConfirm;

    /*-------拼团参数 start----------*/
    @Schema(description = "是否拼团")
    private Boolean openGroupon;

    @Schema(description = "团号")
    private String grouponNo;

    @Schema(description = "购买商品skuId")
    private String skuId;

    @Schema(description = "购买数量")
    private Long num;

    @Schema(description = "分享人Id")
    private String shareUserId;
    /*-------拼团参数 end----------*/

    @Schema(description = "砍价id")
    private Long bargainId;

    @Schema(description = "配送期数")
    private Integer deliveryCycleNum;

    @Schema(description = "首期送达时间")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate deliveryDate;

    /**
     * 选中送达时间
     */
    @Schema(description = "选中送达时间")
    private List<String> deliveryDateList;


    @Schema(description = "用户礼品卡ID")
    private Long userGiftCardId;

    @Schema(description = "社区购参数")
    private CommunityBuyRequest communityBuyRequest;
}
