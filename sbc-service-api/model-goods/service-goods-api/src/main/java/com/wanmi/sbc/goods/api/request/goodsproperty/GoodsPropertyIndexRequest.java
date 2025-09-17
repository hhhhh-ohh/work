package com.wanmi.sbc.goods.api.request.goodsproperty;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author houshuai
 * @date 2021/4/25 14:17
 * @description <p> </p>
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsPropertyIndexRequest extends BaseRequest {


    private static final long serialVersionUID = 5035763220137449263L;
    /**
     * 属性id
     */
    @Schema(description = "属性id")
    @NotNull
    private Long propId;

    /**
     * 是否索引
     */
    @NotNull
    @Schema(description = "是否索引")
    private DefaultFlag indexFlag;



}
