package com.wanmi.sbc.goods.api.request.freight;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * 根据运费模板id获取店铺运费模板请求数据结构
 * Created by daiyitian on 2018/5/3.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreightTemplateStoreByIdRequest extends BaseRequest {

    private static final long serialVersionUID = -1414595202474480030L;

    /**
     * 运费模板id
     */
    @Schema(description = "运费模板id")
    @NotNull
    private Long freightTempId;

}
