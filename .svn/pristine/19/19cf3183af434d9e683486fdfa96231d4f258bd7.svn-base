package com.wanmi.sbc.goods.api.request.freight;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 根据单品运费模板id验证单品运费模板请求
 * Created by daiyitian on 2018/10/31.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreightTemplateGoodsExistsByIdRequest extends BaseRequest {

    private static final long serialVersionUID = 5460138275949320423L;

    /**
     * 单品运费模板id
     */
    @Schema(description = "单品运费模板id")
    @NotNull
    private Long freightTempId;

}
