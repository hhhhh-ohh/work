package com.wanmi.sbc.empower.api.response.channel.goods;

import com.wanmi.sbc.empower.bean.dto.channel.base.goods.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * @author zhengyang
 * @className ChannelGoodsResponse
 * @description 渠道商品返回对象
 * @date 2021/5/23 10:47
 **/
@Data
@Builder
@Schema
@AllArgsConstructor
@NoArgsConstructor
public class ChannelGoodsSyncQueryResponse implements Serializable {

    private String errorCode;

    private String errorMessage;

    /**
     * 商品信息
     */
    @Valid
    @Schema(description = "商品信息")
    private ChannelGoodsDto goods;

    /**
     * 商品相关图片
     */
    @Valid
    @Schema(description = "商品相关图片")
    private List<ChannelGoodsImageDto> images;

    /**
     * 商品规格列表
     */
    @Schema(description = "商品规格列表")
    private List<ChannelGoodsSpecDto> goodsSpecs;

    /**
     * 商品规格值列表
     */
    @Schema(description = "商品规格值列表")
    private List<ChannelGoodsSpecDetailDto> goodsSpecDetails;

    /**
     * 商品SKU列表
     */
    @Valid
    @Schema(description = "商品SKU列表")
    private List<ChannelGoodsInfoDto> goodsInfos;
}
