package com.wanmi.sbc.goods.api.request.storecate;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 列出二个店铺类型是否存在重合id
 * Author: dyt
 * Time: 2022/04/29.10:22
 */
@Schema
@Data
public class StoreCateCoincideRequest extends BaseRequest {

    @Schema(description = "店铺分类标识")
    private List<Long> storeCateIds;

    @Schema(description = "目标店铺分类标识")
    private List<Long> storeCateSecIds;
}
