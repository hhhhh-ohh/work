package com.wanmi.sbc.order.api.request.follow;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class FollowListRequest extends BaseQueryRequest {

    /**
     * 编号
     */
    @Schema(description = "编号")
    private List<Long> followIds;

    /**
     * SKU编号
     */
    @Schema(description = "SKU编号")
    private String goodsInfoId;

    /**
     * 批量SKU编号
     */
    @Schema(description = "批量SKU编号")
    private List<String> goodsInfoIds;

    /**
     * 收藏标识
     */
    @Schema(description = "收藏标识",contentSchema = com.wanmi.sbc.order.bean.enums.FollowFlag.class)
    private Integer followFlag;

    /**
     * 会员编号
     */
    @Schema(description = "会员id")
    private String customerId;

    /**
     * 客户等级
     */
    @Schema(description = "客户等级id")
    private Long customerLevelId;

    /**
     * 客户等级折扣
     */
    @Schema(description = "客户等级折扣")
    private BigDecimal customerLevelDiscount;

    @Schema(description = "公司信息ID")
    private Long companyInfoId;

    @Schema(description = "是否周期购商品")
    private Integer isBuyCycle;
}
