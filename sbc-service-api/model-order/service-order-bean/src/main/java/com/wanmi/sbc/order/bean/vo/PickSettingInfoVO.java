package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @className PickSettingInfoVO
 * @description TODO
 * @author 黄昭
 * @date 2021/9/10 10:53
 **/
@Data
@Schema
public class PickSettingInfoVO extends BasicResponse {

    /**
     * 自提点Id
     */
    @Schema(description = "自提点Id")
    private Long id;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 自提点名称
     */
    @Schema(description = "自提点名称")
    private String name;

    /**
     * 省份
     */
    @Schema(description = "省份")
    private Long provinceId;

    /**
     * 省份名称
     */
    @Schema(description = "省份名称")
    private String provinceName;

    /**
     * 市
     */
    @Schema(description = "市")
    private Long cityId;

    /**
     * 市名称
     */
    @Schema(description = "市名称")
    private String cityName;

    /**
     * 区
     */
    @Schema(description = "区")
    private Long areaId;

    /**
     * 区名称
     */
    @Schema(description = "区名称")
    private String areaName;

    /**
     * 街道
     */
    @Schema(description = "街道")
    private Long streetId;

    /**
     * 街道名称
     */
    @Schema(description = "街道名称")
    private String streetName;

    /**
     * 详细街道地址
     */
    @Schema(description = "店铺ID")
    private String pickupAddress;

    /**
     * skuIds
     */
    @Schema(description = "skuIds")
    private List<String> skuIds;

    /**
     * 自提时间说明
     */
    @Schema(description = "自提时间说明")
    private String remark;

    /**
     * 经度
     */
    @Schema(description = "经度")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @Schema(description = "纬度")
    private BigDecimal latitude;

    /**
     * 自提点联系电话
     */
    @Schema(description = "自提点联系电话")
    private String phone;

    /**
     * 自提点区号
     */
    @Schema(description = "自提点区号")
    private String areaCode;

    /**
     * 自提点到收货地址的距离
     */
    @Schema(description = "自提点到收货地址的距离")
    private Long distance;
}
