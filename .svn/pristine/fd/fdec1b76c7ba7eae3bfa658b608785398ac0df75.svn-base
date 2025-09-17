package com.wanmi.sbc.goods.api.request.goodsevaluate;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author houshuai
 * @date 2021/5/25 14:58
 * @description <p> 修改是否展示 </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
@Builder
public class GoodsEvaluateModifyIsShowRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    @Schema(description = "主键")
    private String evaluateId;

    /**
     * 是否展示
     */
    @Schema(description = "是否展示 0 否 ，1 是")
    @NotNull
    private Integer isShow;


}
