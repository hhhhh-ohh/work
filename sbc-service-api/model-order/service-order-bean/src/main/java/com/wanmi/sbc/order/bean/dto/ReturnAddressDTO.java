package com.wanmi.sbc.order.bean.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author：zhangwenchang
 * @Date：2020/11/4 09:48
 * @Description：退货地址
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnAddressDTO implements Serializable {

    /**
     * addressId Id
     */
    @Schema(description = "addressId Id")
    private String id;

    /**
     * 省
     */
    @Schema(description = "省")
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
     * 详细地址
     */
    @Schema(description = "详细地址")
    private String address;

    /***
     * 详细地址(包含省市区）
     */
    @Schema(description = "详细地址(包含省市区）")
    private String detailAddress;

    /**
     * 收货人名称
     */
    @Schema(description = "收货人名称")
    private String name;

    /**
     * 收货人电话
     */
    @Schema(description = "收货人电话")
    private String phone;

}
