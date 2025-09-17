package com.wanmi.sbc.goods.request;

import com.wanmi.sbc.goods.api.request.cate.GoodsCateModifyRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-13 15:42
 */
@Schema
@Data
public class GoodsCateModify {

    @Schema(description = "商品分类")
    private GoodsCateModifyRequest goodsCate;

}
