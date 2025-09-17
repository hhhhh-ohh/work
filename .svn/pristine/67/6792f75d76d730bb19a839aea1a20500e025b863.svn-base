package com.wanmi.sbc.goods.api.request.freight;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 根据店铺id和删除状态查询店铺运费模板请求数据结构
 * Created by daiyitian on 2018/5/3.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreightTemplateStoreListByStoreIdAndDeleteFlagRequest extends BaseRequest {

    private static final long serialVersionUID = -3299897039181233329L;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;

    /**
     * 删除状态
     */
    @Schema(description = "删除状态，0: 否, 1: 是")
    @NotNull
    private DeleteFlag deleteFlag;
}
