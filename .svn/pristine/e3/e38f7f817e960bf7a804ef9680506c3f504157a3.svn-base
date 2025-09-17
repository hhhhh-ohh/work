package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
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
public class ReturnAddressVO extends BasicResponse {

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
    @SensitiveWordsField(signType = SignWordType.ADDRESS)
    private String address;

    /***
     * 详细地址(包含省市区）
     */
    @Schema(description = "详细地址(包含省市区）")
    @SensitiveWordsField(signType = SignWordType.ADDRESS)
    private String detailAddress;

    /**
     * 收货人名称
     */
    @Schema(description = "收货人名称")
    @SensitiveWordsField(signType = SignWordType.NAME)
    private String name;

    /**
     * 收货人电话
     */
    @Schema(description = "收货人电话")
    @SensitiveWordsField(signType = SignWordType.PHONE)
    private String phone;

    /**
     * 省
     */
    @Schema(description = "省")
    private String provinceName;

    /**
     * 市
     */
    @Schema(description = "市")
    private String cityName;

    /**
     * 区
     */
    @Schema(description = "区")
    private String areaName;

    /**
     * 街道
     */
    @Schema(description = "街道")
    private String streetName;

}
