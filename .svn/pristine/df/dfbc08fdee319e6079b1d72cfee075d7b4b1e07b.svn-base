package com.wanmi.sbc.customer.api.request.level;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 会员等级参数
 * Created by CHENLI on 2017/4/17.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerLevelEditRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 客户等级ID
     */
    @Schema(description = "客户等级ID")
    private Long customerLevelId;

    /**
     * 客户等级名称
     */
    @Schema(description = "客户等级名称")
    @NotNull
    private String customerLevelName;

    /**
     * 等级徽章地址
     */
    @Schema(description = "等级徽章地址")
    @NotNull
    private String rankBadgeImg;

    /**
     * 所需成长值
     */
    @Schema(description = "所需成长值")
    @NotNull
    private Long growthValue;

    /**
     * 客户等级折扣
     */
    @Schema(description = "客户等级折扣")
    @NotNull
    private BigDecimal customerLevelDiscount;

    /**
     * 会员权益id
     */
    @Schema(description = "会员权益id")
    private List<Integer> rightsIds;

}
