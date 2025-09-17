package com.wanmi.sbc.goods.api.request.cate;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>根据店铺获取叶子分类列表请求结构</p>
 * @author daiyitian
 * @dateTime 2018/12/19 下午4:41
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCateLeafByStoreIdRequest extends BaseRequest {

    private static final long serialVersionUID = -260368318261518983L;

    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;
}
