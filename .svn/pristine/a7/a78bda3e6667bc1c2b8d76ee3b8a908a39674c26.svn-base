package com.wanmi.sbc.marketing.api.request.communityactivity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.dto.CommunityCommissionAreaRelDTO;
import com.wanmi.sbc.marketing.bean.dto.CommunityCommissionLeaderRelDTO;
import com.wanmi.sbc.marketing.bean.dto.CommunitySkuRelDTO;
import com.wanmi.sbc.marketing.bean.enums.*;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import lombok.*;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>社区团购活动表新增参数</p>
 *
 * @author dyt
 * @date 2023-07-24 14:26:35
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityActivityAddRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 活动名称
     */
    @Schema(description = "活动名称")
    @NotBlank
    @Length(min = 1, max = 80)
    private String activityName;

    /**
     * 活动描述
     */
    @Schema(description = "活动描述")
    @NotBlank
    @Length(min = 1, max = 400)
    private String description;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    @NotNull
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    @NotNull
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 物流方式 0:自提 1:快递 以逗号拼凑
     */
    @Schema(description = "物流方式 0:自提 1:快递")
    @NotEmpty
    private List<CommunityLogisticsType> logisticsTypes;

    /**
     * 销售渠道 0:自主销售 1:团长帮卖 以逗号拼凑
     */
    @Schema(description = "销售渠道 0:自主销售 1:团长帮卖")
    @NotEmpty
    private List<CommunitySalesType> salesTypes;

    /**
     * 自主销售范围 0：全部 1：地区 2：自定义
     */
    @Schema(description = "自主销售范围 0：全部 1：地区 2：自定义")
    @NotNull
    private CommunitySalesRangeType salesRange;

    /**
     * 自主销售范围内容
     */
    @Schema(description = "自主销售范围内容")
    private List<String> salesRangeContext;

    /**
     * 自主销售范围区域名称
     */
    @Schema(description = "自主销售范围区域名称")
    private List<String> salesRangeAreaNames;

    /**
     * 帮卖团长范围 0：全部 1：地区 2：自定义
     */
    @Schema(description = "帮卖团长范围 0：全部 1：地区 2：自定义")
    @NotNull
    private CommunityLeaderRangeType leaderRange;

    /**
     * 帮卖团长范围内容
     */
    @Schema(description = "帮卖团长范围内容")
    private List<String> leaderRangeContext;

    /**
     * 帮卖团长范围内容
     */
    @Schema(description = "帮卖团长范围区域名称")
    private List<String> leaderRangeAreaNames;

    /**
     * 佣金设置 0：商品 1：按团长/自提点
     */
    @Schema(description = "佣金设置 0：商品 1：按团长/自提点")
    @NotNull
    private CommunityCommissionFlag commissionFlag;

    /**
     * 批量-自提服务佣金
     */
    @Schema(description = "批量-自提服务佣金")
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal pickupCommission;

    /**
     * 批量-帮卖团长佣金
     */
    @Schema(description = "批量-帮卖团长佣金")
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal assistCommission;

    /**
     * 区域佣金
     */
    @Valid
    @Schema(description = "区域佣金")
    private List<CommunityCommissionAreaRelDTO> commissionAreaList;

    /**
     * 团长佣金
     */
    @Valid
    @Schema(description = "团长佣金")
    private List<CommunityCommissionLeaderRelDTO> commissionLeaderList;

    /**
     * 商品列表
     */
    @Valid
    @NotEmpty
    @Schema(description = "商品列表")
    private List<CommunitySkuRelDTO> skuList;

    /**
     * 团详情
     */
    @Schema(description = "团详情")
    private String details;

    /**
     * 团图片
     */
    @Schema(description = "团图片")
    private String images;

    /**
     * 团视频
     */
    @Schema(description = "团视频")
    private String videoUrl;

    @Override
    public void checkParam() {
        LocalDateTime now = LocalDateTime.now();
        if (startTime == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (startTime.isBefore(now) || startTime.isEqual(now)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (endTime != null && (endTime.isBefore(startTime) || endTime.isEqual(startTime))) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        if ((!CommunitySalesRangeType.ALL.equals(salesRange)) && CollectionUtils.isEmpty(salesRangeContext)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        if ((!CommunityLeaderRangeType.ALL.equals(leaderRange)) && CollectionUtils.isEmpty(leaderRangeContext)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        if (CommunityCommissionFlag.PICKUP.equals(commissionFlag)) {
            if (pickupCommission == null
                    && assistCommission == null
                    && CollectionUtils.isEmpty(commissionAreaList)
                    && CollectionUtils.isEmpty(commissionLeaderList)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }

            if (CollectionUtils.isNotEmpty(commissionAreaList)
                    && commissionAreaList.stream().anyMatch(c -> c.getAssistCommission() == null
                    && c.getPickupCommission() == null)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }

            if (CollectionUtils.isNotEmpty(commissionLeaderList)
                    && commissionLeaderList.stream().anyMatch(c -> c.getAssistCommission() == null
                    && c.getPickupCommission() == null)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        } else if (CommunityCommissionFlag.GOODS.equals(commissionFlag)
                && skuList.stream().anyMatch(s -> s.getAssistCommission() == null && s.getPickupCommission() == null)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }
}