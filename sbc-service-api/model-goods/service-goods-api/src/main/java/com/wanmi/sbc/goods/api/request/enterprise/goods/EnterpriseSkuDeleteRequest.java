package com.wanmi.sbc.goods.api.request.enterprise.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 企业商品删除
 * @author by 柏建忠 on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseSkuDeleteRequest extends BaseRequest {

    private static final long serialVersionUID = -2921494912778765610L;

    /**
     * skuId
     */
    @Schema(description = "skuId")
    @NotEmpty
    private String goodsInfoId;

}
