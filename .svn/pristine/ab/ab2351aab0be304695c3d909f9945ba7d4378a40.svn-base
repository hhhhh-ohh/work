package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author houshuai
 * @date 2021/5/26 16:25
 * @description <p> 商品推广信息 </p>
 */
@Data
public class GoodsInfoExtendVO extends BasicResponse {


    private static final long serialVersionUID = 1L;

    /**
     *  skuId
     */
    @Schema(description = "skuId")
    private String goodsInfoId;

    /**
     * spuId
     */
    @Schema(description = "spuId")
    private String goodsId;


    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;


    /**
     * 页面所属平台
     */
    @Schema(description = "页面所属平台")
    private String platform;

    /**
     * 背景图
     */
    @Schema(description = "背景图")
    private String backgroundPic;

    /**
     * 使用类型  0:小程序 1:二维码
     */
    @Schema(description = "使用类型  0:小程序 1:二维码")
    private Integer useType;

    /**
     * 小程序码
     */
    @Schema(description = "小程序码")
    private String miniProgramQrCode;

    /**
     * 二维码
     */
    @Schema(description = "二维码")
    private String qrCode;

    /**
     * 默认地址
     */
    @Schema(description = "默认地址")
    private String url;

    /**
     * 渠道标记
     */
    @Schema(description = "渠道标记")
    private List<String> sources;

    /**
     * 图片地址
     */
    @Schema(description = "图片地址")
    private String imageUrl;
}
