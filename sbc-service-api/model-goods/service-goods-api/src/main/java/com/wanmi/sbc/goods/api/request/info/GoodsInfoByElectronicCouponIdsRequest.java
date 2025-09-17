package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xuyunpeng
 * @className GoodsInfoByElectronicCouponIdsRequest
 * @description
 * @date 2023/5/27 10:02
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoByElectronicCouponIdsRequest extends BaseRequest {
    private static final long serialVersionUID = -7476744706238313966L;

    /**
     * 卡券ids
     */
    @Schema(description = "卡券ids")
    @NotEmpty
    private List<Long> electronicCouponIds;
}
