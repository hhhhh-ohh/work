package com.wanmi.sbc.goods.api.response.info;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加分销商品时返回的不符合条件的结果
 *
 * @author CHENLI
 * @dateTime 2019/3/26 上午9:33
 */
@Schema
@Data
public class DistributionGoodsAddResponse extends BasicResponse {

    private static final long serialVersionUID = -6833816999129184466L;

    /**
     * 添加分销商品时返回的不符合条件的结果，skuIds
     */
    @Schema(description = "不符合条件的skuIds")
    private List<String> goodsInfoIds = new ArrayList<>();
}
