package com.wanmi.sbc.marketing.bean.dto;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.StoreType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-19 10:59
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class MarketingPageDTO extends MarketingQueryBaseDTO{


    private static final long serialVersionUID = 1725098078246183990L;

    /**
     * boss端查询客户等级
     */
    @Schema(description = "boss端查询客户等级")
    private Long bossJoinLevel;

    /**
     * 是否是平台，1：boss，0：商家
     */
    @Schema(description = "是否是平台，1：boss，0：商家")
    private BoolFlag isBoss;

    /**
     * 参与店铺是：0全部，1指定店铺
     */
    @Schema(description = "参与店铺是：0全部，1指定店铺")
    private Integer storeType;
}
