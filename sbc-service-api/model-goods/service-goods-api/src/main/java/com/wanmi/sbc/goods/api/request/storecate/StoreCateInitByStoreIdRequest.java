package com.wanmi.sbc.goods.api.request.storecate;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wanggang
 * @version 1.0
 * @createDate 2018/11/1 10:00
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreCateInitByStoreIdRequest extends BaseRequest {

    private static final long serialVersionUID = 880993701773938021L;

    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;
}
