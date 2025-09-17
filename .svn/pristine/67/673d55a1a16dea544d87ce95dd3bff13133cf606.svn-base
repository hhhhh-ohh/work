package com.wanmi.sbc.marketing.bean.dto;

import com.wanmi.sbc.marketing.bean.enums.GiftType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: ZhangLingKe
 * @Description: 营销满赠多级优惠实体
 * @Date: 2018-11-20 14:02
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullGiftLevelDTO implements Serializable {

    private static final long serialVersionUID = 4701841638960976835L;
    /**
     *  满赠多级促销Id
     */
    @Schema(description = "满赠多级促销Id")
    private Long giftLevelId;

    /**
     *  满赠Id
     */
    @Schema(description = "营销id")
    private Long marketingId;

    /**
     *  满金额赠
     */
    @Schema(description = "满金额赠")
    private BigDecimal fullAmount;

    /**
     *  满数量赠
     */
    @Schema(description = "满数量赠")
    private Long fullCount;

    /**
     *  赠品赠送的方式 0:全赠  1：赠一个
     */
    @Schema(description = "赠品赠送的方式")
    private GiftType giftType;

    /**
     * 满赠赠品明细
     */
    @Schema(description = "满赠赠品明细列表")
    private List<FullGiftDetailDTO> fullGiftDetailList;

}
