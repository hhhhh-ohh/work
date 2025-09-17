package com.wanmi.sbc.marketing.api.request.preferential;

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
 * @author edz
 * @className PreferentialLevelAddRequest
 * @description 加价购档次区间
 * @date 2022/11/17 14:37
 **/
@Data
@Schema
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreferentialLevelRequest implements Serializable {

    @Schema(description = "加价购档次区间主键")
    private Long preferentialLevelId;

    @Schema(description = "加价购活动ID")
    private Long marketingId;

    @Schema(description = "满金额")
    private BigDecimal fullAmount;

    @Schema(description = "满数量")
    private Long fullCount;

    @Schema(description = "加购规则")
    private GiftType preferentialType;

    @Schema(description = "关联商品信息")
    private List<PreferentialGoodsRequest> preferentialDetailList;

}
