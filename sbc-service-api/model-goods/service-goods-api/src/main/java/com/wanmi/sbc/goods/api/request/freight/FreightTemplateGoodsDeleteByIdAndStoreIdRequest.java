package com.wanmi.sbc.goods.api.request.freight;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.PluginType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 根据单品运费模板id和店铺id删除单品运费模板请求
 * Created by daiyitian on 2018/10/31.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreightTemplateGoodsDeleteByIdAndStoreIdRequest extends BaseRequest {

    private static final long serialVersionUID = 8155472793920539921L;

    /**
     * 单品运费模板id
     */
    @Schema(description = "单品运费模板id")
    @NotNull
    private Long freightTempId;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;

    /***
     * 路由键
     */
    private PluginType pluginType = PluginType.NORMAL;
}
