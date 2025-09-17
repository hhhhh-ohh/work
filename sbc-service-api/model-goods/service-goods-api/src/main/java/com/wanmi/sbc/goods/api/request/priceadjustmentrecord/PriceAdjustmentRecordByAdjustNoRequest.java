package com.wanmi.sbc.goods.api.request.priceadjustmentrecord;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询调价记录表请求参数</p>
 * @author
 * @date 2020-12-09 19:57:21
 */

@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceAdjustmentRecordByAdjustNoRequest extends BaseRequest {

    private static final long serialVersionUID = 5521321277291020101L;

    /**
     * 调价单号
     */
    @Schema(description = "调价单号")
    @NotNull
    private String adjustNo;
}
