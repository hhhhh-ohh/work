package com.wanmi.sbc.marketing.bean.dto;

import com.wanmi.sbc.marketing.bean.enums.GiftType;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * <p>满赠</p>
 * author: sunkun
 * Date: 2018-11-19
 */
@Schema
@Data
public class MarketingFullGiftLevelDTO implements Serializable {

    private static final long serialVersionUID = -9102002765312886899L;

    /**
     *  满赠多级促销Id
     */
    @Schema(description = "满赠多级促销主键Id")
    private Long giftLevelId;

    /**
     *  满赠Id
     */
    @Schema(description = "满赠营销Id")
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
    private List<MarketingFullGiftDetailDTO> fullGiftDetailList;

}
