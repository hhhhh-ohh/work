package com.wanmi.sbc.marketing.api.request.bargaingoods;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.dto.BargainGoodsInfoForAddDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>砍价商品活动维度新增参数</p>
 *
 * @author
 * @date 2022-05-20 09:59:19
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BargainGoodsActivityAddRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 活动开始时间
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
    @NotNull
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 是否包邮
     */
    @Schema(description = "是否包邮")
    @NotNull
    private DeleteFlag freightFreeFlag;

    /**
     * 砍价商品列表
     */
    @NotEmpty
    @Valid
    @Schema(description = "砍价商品列表")
    List<BargainGoodsInfoForAddDTO> goodsInfos;

    /**
     * 店铺Id
     */
    @Schema(description = "店铺Id")
    private Long storeId;

    /**
     * 基础参数校验
     */
    public void validate() {
        if (beginTime.isBefore(LocalDateTime.now())) {
            // 开始时间不可早于当前时间
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080136);
        }
        if (endTime.isBefore(beginTime) || endTime.isEqual(beginTime)) {
            // 结束时间不可早于或等于开始时间
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080137);
        }
        int distinctSkuIdCount = (int) goodsInfos.stream().map(BargainGoodsInfoForAddDTO::getGoodsInfoId).distinct().count();
        if (goodsInfos.size() > distinctSkuIdCount) {
            // 选择商品存在重复的skuId
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (goodsInfos.size() > Constants.MARKETING_GOODS_SIZE_MAX) {
            // 选择商品数超过最大支持数
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080027, new Object[]{Constants.MARKETING_GOODS_SIZE_MAX});
        }
    }
}