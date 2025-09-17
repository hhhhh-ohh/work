package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.marketing.bean.enums.GiftType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author edz
 * @className MarketingPreferentialLevelVO
 * @description 加价购档次阶梯实体
 * @date 2022/11/18 11:13
 **/
@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class MarketingPreferentialLevelVO implements Serializable {

    @Schema(description = "主键")
    private Long preferentialLevelId;

    @Schema(description = "加价购活动ID")
    private Long marketingId;

    @Schema(description = "满金额")
    private BigDecimal fullAmount;

    @Schema(description = "满数量")
    private Long fullCount;

    @Schema(description = "0:可全选  1：选一个")
    private GiftType preferentialType;

    @Schema(description = "关联商品信息")
    private List<MarketingPreferentialGoodsDetailVO> preferentialDetailList;
}
