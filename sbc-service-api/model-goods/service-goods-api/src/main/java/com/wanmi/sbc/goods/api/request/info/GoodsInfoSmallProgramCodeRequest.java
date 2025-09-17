package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Created by feitingting on 2019/1/8.
 */
@Schema
@Data
public class GoodsInfoSmallProgramCodeRequest extends BaseRequest {
    /**
     * sku编号
     */
    @Schema(description = "sku编号")
    private String goodsInfoId;

    /**
     * 小程序码地址
     */
    @Schema(description = "小程序码地址")
    private String codeUrl;
}

