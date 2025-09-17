package com.wanmi.sbc.goods.api.request.storecate;


import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 黄昭
 * @className StoreCateListByParentIdRequest
 * @description TODO
 * @date 2022/4/18 15:35
 **/
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreCateListByParentIdRequest {

    @Schema(description = "父分类ID")
    private Long parentId;

    @Schema(description = "店铺Id")
    private Long storeId;
}