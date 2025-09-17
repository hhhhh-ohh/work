package com.wanmi.sbc.order.trade.model.entity;/**
 * @author 黄昭
 * @create 2021/9/10 10:10
 */


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @className PickSettingInfo
 * @description TODO
 * @author 黄昭
 * @date 2021/9/10 10:10
 **/
@Data
@Schema
public class PickSettingInfo {

    /**
     * 自提点Id
     */
    @Schema(description = "自提点Id")
    private Long id;

    /**
     * 门店Id
     */
    @Schema(description = "门店Id")
    private Long storeId;

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
     * 市
     */
    @Schema(description = "市")
    private Long cityId;

    /**
     * 区
     */
    @Schema(description = "区")
    private Long areaId;

    /**
     * 街道
     */
    @Schema(description = "街道")
    private Long streetId;

    /**
     * 详细街道地址
     */
    @Schema(description = "详细街道地址")
    private String pickupAddress;

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
     * 自提点区号
     */
    @Schema(description = "自提点区号")
    private String areaCode;

    /**
     * 自提点联系电话
     */
    @Schema(description = "自提点联系电话")
    private String phone;

    /**
     * 自提点到收货地址的距离
     */
    @Schema(description = "自提点到收货地址的距离")
    private Long distance;
}
