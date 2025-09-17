package com.wanmi.sbc.goods.api.request.storecate;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 店铺分类统计数量请求参数
 * @author dyt
 * @version 1.0
 * @createDate 2022/4/29 10:00
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreCateGoodsRelaCountRequest extends BaseRequest {

    private static final long serialVersionUID = -5068649678829183110L;

    @Schema(description = "商品Id")
    private List<String> goodsIds;

    @Schema(description = "店铺分类Id")
    private List<Long> storeCateIds;
}
