package com.wanmi.sbc.goods.api.request.freight;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 店铺运费模板分页请求数据结构
 * Created by daiyitian on 2018/5/3.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class FreightTemplateStorePageRequest extends BaseQueryRequest {

    private static final long serialVersionUID = -8107078231538944644L;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;

}
