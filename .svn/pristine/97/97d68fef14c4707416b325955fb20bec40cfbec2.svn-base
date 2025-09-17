package com.wanmi.sbc.goods.api.request.freight;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * 根据运费模板id和店铺id获取区域id请求数据结构
 * Created by daiyitian on 2018/5/3.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreightTemplateStoreAreaIdByIdAndStoreIdRequest extends BaseRequest {

    private static final long serialVersionUID = -1886348992702980751L;

    /**
     * 运费模板id
     */
    @Schema(description = "运费模板id")
    @NotNull
    private Long freightTempId;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;
}
