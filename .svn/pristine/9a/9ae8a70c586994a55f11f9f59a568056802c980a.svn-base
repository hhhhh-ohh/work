package com.wanmi.sbc.goods.api.request.standard;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.*;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>批量新增商品库请求类</p>
 * Date: 2018-12-20
 * @author dyt
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandardGoodsBatchAddRequest extends BaseRequest {

    private static final long serialVersionUID = 6661064796320216064L;

    /**
     * 商品库批量信息
     */
    @Schema(description = "商品库批量信息")
    @NotEmpty
    private List<BatchStandardGoodsDTO> goodsList;

    /**
     * 商品库SKU批量信息
     */
    @Schema(description = "商品库SKU批量信息")
    @NotEmpty
    private List<BatchStandardSkuDTO> skuList;

    /**
     * 商品库规格批量信息
     */
    @Schema(description = "商品库规格批量信息")
    private List<BatchStandardSpecDTO> specList;

    /**
     * 商品库规格值批量信息
     */
    @Schema(description = "商品库规格值批量信息")
    private List<BatchStandardSpecDetailDTO> specDetailList;

    /**
     * 图片批量信息
     */
    @Schema(description = "图片批量信息")
    private List<BatchStandardImageDTO> imageList;
}

