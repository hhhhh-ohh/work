package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 根据商品SKU编号查询请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCacheInfoByIdRequest extends BaseRequest {

    private static final long serialVersionUID = 2095538333302869168L;

    /**
     * SKU编号
     */
    @Schema(description = "SKU编号")
    @NotBlank
    private String goodsInfoId;

    @Schema(description = "店铺Id")
    private Long storeId;


    @Schema(description = "customerId")
    private String customerId;

    @Schema(description = "是否需要返回标签数据 true:需要，false或null:不需要")
    private Boolean showLabelFlag;

    @Schema(description = "当showLabelFlag=true时，true:返回开启状态的标签，false或null:所有标签")
    private Boolean showSiteLabelFlag;

}
