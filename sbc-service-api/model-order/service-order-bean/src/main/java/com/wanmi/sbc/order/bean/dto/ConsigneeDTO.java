package com.wanmi.sbc.order.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 收货人信息
 * Created by jinwei on 19/03/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ConsigneeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * CustomerDeliveredAddress Id
     */
    @Schema(description = "CustomerDeliveredAddress Id")
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

    @Schema(description = "街道")
    private Long streetId;

    /**
     * 详细地址
     */
    @Schema(description = "详细地址")
    private String address;
    /**
     * 身份证号码18位 冗余长度防止没有trim
     */
    @Schema(description = "身份证号码18位")
    private String customerCardNo;
    /**
     * 身份证姓名
     */
    @Schema(description = "身份证姓名")
    private String customerCardName;
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

    /**
     * 期望收货时间
     */
    @Schema(description = "期望收货时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime expectTime;

    /**
     * 收货地址修改时间
     */
    @Schema(description = "收货地址修改时间")
    private String updateTime;

    /**
     * 收货地址
     */
    @Schema(description = "收货地址")
    private String shippingAddress;

    /**
     * 门牌号
     */
    @Schema(description = "门牌号")
    private String houseNum;

    /**
     * 纬度
     */
    @Schema(description = "纬度")
    private BigDecimal latitude;

    /**
     * 经度
     */
    @Schema(description = "经度")
    private BigDecimal longitude;
}
