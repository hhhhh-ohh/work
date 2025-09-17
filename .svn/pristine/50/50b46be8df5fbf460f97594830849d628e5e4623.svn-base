package com.wanmi.sbc.goods.api.response.goods;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.response.goods.GoodsAddAllResponse
 * 新增商品基本信息、基价响应对象
 *
 * @author lipeng
 * @dateTime 2018/11/5 上午10:39
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsSynResponse extends BasicResponse {

    private static final long serialVersionUID = 8220760678488489421L;

    @Schema(description = "商品Id")
    private List<String> goodsIds = new ArrayList<>();
}
