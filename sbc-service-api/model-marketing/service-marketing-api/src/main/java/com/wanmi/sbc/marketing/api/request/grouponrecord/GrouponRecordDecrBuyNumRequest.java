package com.wanmi.sbc.marketing.api.request.grouponrecord;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>拼团活动参团信息表新增参数</p>
 * @author groupon
 * @date 2019-05-17 16:17:44
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrouponRecordDecrBuyNumRequest extends BaseRequest {

    /**
     * 拼团活动ID
     */
    @Schema(description = " 拼团活动ID")
    @NotBlank
    private String grouponActivityId;

    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    @NotBlank
    private String customerId;

    /**
     * sku编号
     */
    @Schema(description = "SKU编号")
    @NotBlank
    private String goodsInfoId;

    /**
	 * 购买数
     */
    @Schema(description = "购买数")
    @NotNull
    private Integer buyNum;

}