package com.wanmi.sbc.goods.api.request.common;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.goods.bean.dto.BatchGoodsDTO;
import com.wanmi.sbc.goods.bean.dto.BatchGoodsImageDTO;
import com.wanmi.sbc.goods.bean.dto.BatchGoodsInfoDTO;
import com.wanmi.sbc.goods.bean.dto.BatchGoodsSpecDTO;
import com.wanmi.sbc.goods.bean.dto.BatchGoodsSpecDetailDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 批量导入商品信息请求对象
 * @author daiyitian
 * @dateTime 2018/11/2 上午9:54
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCommonBatchAddRequest extends BaseRequest {

    private static final long serialVersionUID = 433831762426623640L;

    /**
     * 商品批量信息
     */
    @Schema(description = "商品批量信息")
    @NotEmpty
    private List<BatchGoodsDTO> goodsList;

    /**
     * 商品SKU批量信息
     */
    @Schema(description = "商品SKU批量信息")
    @NotEmpty
    private List<BatchGoodsInfoDTO> goodsInfoList;

    /**
     * 商品规格批量信息
     */
    @Schema(description = "商品规格批量信息")
    private List<BatchGoodsSpecDTO> specList;

    /**
     * 商品规格值批量信息
     */
    @Schema(description = "商品规格值批量信息")
    private List<BatchGoodsSpecDetailDTO> specDetailList;

    /**
     * 图片批量信息
     */
    @Schema(description = "图片批量信息")
    private List<BatchGoodsImageDTO> imageList;

    /**
     * Excel模板类型 0:商家,1:供应商
     */
    @Schema(description = "Excel模板类型")
    private StoreType type;

    /**
     * 登录信息
     */
    @Schema(description = "登录信息")
    private OperationLogAddRequest operationLogAddRequest;

    /**
     * 商品插件类型
     */
    private PluginType pluginType = PluginType.NORMAL;
}
