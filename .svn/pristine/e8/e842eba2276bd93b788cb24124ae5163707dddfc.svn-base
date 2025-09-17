package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 黄昭
 * @className GoodsAuditViewByIdRequest
 * @description
 * @date 2021/12/21 17:19
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsAuditViewByIdRequest extends BaseRequest {
    private static final long serialVersionUID = -5187832889514993766L;

    @Schema(description = "商品Id")
    @NotBlank
    private String goodsId;

    @Schema(description = "是否需要返回标签数据 true:需要，false或null:不需要")
    private Boolean showLabelFlag;

    @Schema(description = "当showLabelFlag=true时，true:返回开启状态的标签，false或null:所有标签")
    private Boolean showSiteLabelFlag;

    @Schema(description = "是否是编辑供应商商品")
    private Boolean isEditProviderGoods = Boolean.FALSE;

}