package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 会员收货地址-共用VO
 */
@Schema
@Data
public class CustomerDeliveryAddressVO extends BasicResponse {

    private static final long serialVersionUID = 1L;
    /**
     * 收货地址ID
     */
    @Schema(description = "收货地址ID")
    private String deliveryAddressId;
    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private String customerId;

    /**
     * 收货人
     */
    @Schema(description = "收货人")
    @SensitiveWordsField(signType = SignWordType.NAME)
    private String consigneeName;

    /**
     * 收货人手机号码
     */
    @Schema(description = "收货人手机号码")
    @SensitiveWordsField(signType = SignWordType.PHONE)
    private String consigneeNumber;

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
    private String deliveryAddress;

    /**
     * 是否是默认地址 0：否 1：是
     */
    @Schema(description = "是否是默认地址")
    private DefaultFlag isDefaltAddress;

    /**
     * 删除标志 0未删除 1已删除
     */
    @Schema(description = "删除标志")
    private DeleteFlag delFlag;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createPerson;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private String updatePerson;

    /**
     * 删除时间
     */
    @Schema(description = "删除时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime deleteTime;

    /**
     * 删除人
     */
    @Schema(description = "删除人")
    private String deletePerson;

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
    @SensitiveWordsField(signType = SignWordType.ADDRESS)
    private String streetName;

    /**
     * 是否需要完善,true表示需要，false表示不需要
     */
    @Schema(description = "是否需要完善,true表示需要，false表示不需要")
    private Boolean needComplete;

    /**
     * 经度
     */
    @Schema(description = "经度")
    private BigDecimal latitude;

    /**
     * 纬度
     */
    @Schema(description = "纬度")
    private BigDecimal longitude;

    /**
     * GEOHash值（12位）
     */
    @Schema(description = "GEOHash值（12位）")
    private String geoHash;

    /**
     * 门牌号
     */
    @Schema(description = "门牌号")
    @SensitiveWordsField(signType = SignWordType.ADDRESS)
    private String houseNum;
}
