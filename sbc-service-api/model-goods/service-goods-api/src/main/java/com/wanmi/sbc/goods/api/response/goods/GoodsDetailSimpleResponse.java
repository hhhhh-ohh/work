package com.wanmi.sbc.goods.api.response.goods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询商品简易信息
 *
 * @author yangzhen
 * @date 2020/9/3 11:31
 */
@Schema
@Data
public class GoodsDetailSimpleResponse extends BasicResponse {

    private static final long serialVersionUID = -1588688058461927154L;

    /**
     * 商品简易信息
     */
    @Schema(description = "商品简易信息")
    private GoodsVO goods;
}
