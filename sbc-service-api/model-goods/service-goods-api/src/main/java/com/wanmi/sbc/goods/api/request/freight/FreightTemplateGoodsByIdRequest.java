package com.wanmi.sbc.goods.api.request.freight;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 根据运费模板id查询单品运费模板请求
 * Created by daiyitian on 2018/10/31.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreightTemplateGoodsByIdRequest extends BaseRequest {

    private static final long serialVersionUID = 624594521287936801L;

    /**
     * 运费模板id
     */
    @Schema(description = "运费模板id")
    @NotNull
    private Long freightTempId;

}
