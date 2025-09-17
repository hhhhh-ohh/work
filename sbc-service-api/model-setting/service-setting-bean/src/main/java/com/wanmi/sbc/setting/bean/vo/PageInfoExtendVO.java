package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 物流记录
 * Created by dyt on 2020/4/17.
 */
@Data
public class PageInfoExtendVO extends BasicResponse {


    private static final long serialVersionUID = 1L;
    /**
     * pageId
     */
    @Schema(description = "页面id")
    private String pageId;


    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 页面code
     */
    @Schema(description = "页面code")
    private String pageCode;

    /**
     * 页面类型
     */
    @Schema(description = "页面类型")
    private String pageType;

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
    private String MiniProgramQrCode;

    /**
     * 二维码
     */
    @Schema(description = "二维码")
    private String qrCode;

    /**
     * 默认地址
     */
    @Schema(description = "默认地址")
    private String Url;

    /**
     * 渠道标记
     */
    @Schema(description = "渠道标记")
    private List<String> sources;

    /**
     * 优惠券活动ID
     */
    private String activityId;

    /**
     * 券ID
     */
    private String couponId;

    /**
     * 小程序码地址
     */
    @Schema(description = "小程序码地址")
    private String imageUrl;

    /**
     * 营销活动类型
     */
    @Schema(description = "营销活动类型")
    private Integer marketingType;


    /**
     * 营销活动类型
     */
    @Schema(description = "商品图片")
    private String goodsInfoImg;

}
