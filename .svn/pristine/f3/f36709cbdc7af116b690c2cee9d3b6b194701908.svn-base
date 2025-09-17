package com.wanmi.sbc.order.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.CommunityCommissionFlag;
import com.wanmi.sbc.marketing.bean.enums.CommunitySalesType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author edz
 * @className CommunityTradeCommission
 * @description 社区团购佣金信息
 * @date 2023/7/26 11:30
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class CommunityTradeCommissionVO implements Serializable {

    private static final long serialVersionUID = -1501379562459844656L;

    @Schema(description = "活动ID")
    private String activityId;

    @Schema(description = "佣金设置 0：商品 1：按团长/自提点")
    private CommunityCommissionFlag commissionFlag;

    @Schema(description = "佣金费率")
    private BigDecimal commissionRate;

    @Schema(description = "佣金")
    private BigDecimal totalCommission;

    @Schema(description = "入账标识")
    private BoolFlag boolFlag;

    @Schema(description = "团长ID")
    private String leaderId;

    @Schema(description = "团长会员id")
    private String customerId;

    @SensitiveWordsField(signType = SignWordType.NAME)
    @Schema(description = "团长名称")
    private String leaderName;

    @SensitiveWordsField(signType = SignWordType.PHONE)
    @Schema(description = "团长手机号")
    private String leaderPhone;

    @Schema(description = "团购类型")
    private CommunitySalesType salesType;

    @Schema(description = "自提点ID")
    private String pickupPointId;

    @Schema(description = "入账时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime settlementTime;

    @Schema(description = "跟团号")
    private Long activityTradeNo;

    @Schema(description = "商品信息")
    private List<GoodsInfoItemVO> goodsInfoItem;

    @Data
    @Schema
    public static class GoodsInfoItemVO {
        @Schema(description = "商品ID")
        private String goodsInfoId;
        @Schema(description = "均摊价(产生退单会减去已退的)")
        private BigDecimal price;
        @Schema(description = "购买数量(产生退单会减去已退的)")
        private Long num;
        @Schema(description = "佣金费率")
        private BigDecimal commissionRate;
        @Schema(description = "佣金")
        private BigDecimal commission;

    }
}
