package com.wanmi.sbc.marketing.api.request.marketingsuits;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.dto.GoodsSuitsDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingSuitsSaveRequest extends BaseRequest {

    /**
     * 活动id
     */
    @Schema(description = "活动id")
    private Long marketingId;


    /**
     * 促销名称
     */
    @Schema(description = "促销名称")
    @NotNull
    private String suitsName;

    /**
     *活动开始时间
     */
    @Schema(description = "活动开始时间")
    @NotNull
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime beginTime;

    /**
     * 活动结束时间
     */
    @Schema(description = "活动结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @NotNull
    private LocalDateTime endTime;

    /**
     * 套餐主图
     */
    @NotNull
    @Schema(description = "套餐主图")
    private String suitsPictureUrl;

    /**
     * 数量是否全部相同标识
     */
    @Schema(description = "数量是否全部相同标识")
    @NotNull
    private DefaultFlag goodsSkuNumIdentify;

    /**
     * 添加组合商品集合
     */
    @Schema(description = "添加组合商品集合")
    private List<GoodsSuitsDTO> goodsSuitsDTOList;

    /**
     * 参加会员 0:全部等级  other:其他等级
     */
    @Schema(description = "参加会员", contentSchema = com.wanmi.sbc.marketing.bean.enums.MarketingJoinLevel.class)
    @NotBlank
    private String joinLevel;

    /**
     * 是否暂停（1：暂停，0：正常）
     */
    @Schema(description = "是否暂停（1：暂停，0：正常）")
    private DefaultFlag isPause;

    @Override
    public void checkParam() {
        if (StringUtils.isBlank(suitsName)
                || StringUtils.isBlank(suitsPictureUrl)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (beginTime.isAfter(endTime)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }

    public void setBeginTime(LocalDateTime beginTime) {
        if (Objects.nonNull(beginTime)){
            beginTime = beginTime.minusSeconds(beginTime.getSecond());
            this.beginTime = beginTime;
        }
    }

    public void setEndTime(LocalDateTime endTime) {
        if (Objects.nonNull(endTime)){
            endTime = endTime.minusSeconds(endTime.getSecond());
            this.endTime = endTime;
        }
    }

}
