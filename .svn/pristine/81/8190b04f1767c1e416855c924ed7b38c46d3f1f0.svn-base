package com.wanmi.sbc.elastic.api.request.distributionmatter;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author houshuai
 * @date 2021/1/12 15:30
 * @description <p> 修改分销素材入参 </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class EsDistributionGoodsMatteByIdRequest extends BaseRequest {


    @NotBlank
    @Schema(description = "分销素材主键")
    private String id;
}
