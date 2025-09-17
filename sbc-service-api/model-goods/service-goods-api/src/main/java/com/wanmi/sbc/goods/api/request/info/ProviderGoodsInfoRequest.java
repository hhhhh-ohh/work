package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description
 * @author  wur
 * @date: 2021/9/24 16:38
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProviderGoodsInfoRequest extends BaseRequest {

    private static final long serialVersionUID = -2265501195719873212L;

    /**
     * 商品sku编码
     */
    @Schema(description = "商品sku编码")
    private List<String> goodsInfoIdList;
}
