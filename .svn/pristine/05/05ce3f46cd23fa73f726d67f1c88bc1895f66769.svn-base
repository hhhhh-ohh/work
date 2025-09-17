package com.wanmi.sbc.empower.bean.vo.sellplatform.order;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wur
 * @className ChannelsCheckAccessInfoVO
 * @description 配送信息
 * @date 2022/4/1 19:52
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformOrderAddressVO implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 收件人姓名
     */
    @NotNull
    @Schema(description = "收件人姓名")
    private String receiver_name;

    /**
     * 收货人详细收货地址信息
     */
    @NotNull
    @Schema(description = "收货人详细收货地址信息")
    private String detailed_address;

    /**
     * 收件人手机号码
     */
    @NotNull
    @Schema(description = "收件人手机号码")
    private String tel_number;

    /**
     * 国家， 可不填
     */
    @Schema(description = "国家， 可不填")
    private String country;

    /**
     * 省份， 可不填
     */
    @Schema(description = "省份， 可不填")
    private String province;

    /**
     * 城市， 可不填
     */
    @Schema(description = "城市， 可不填")
    private String city;

    /**
     * 乡镇， 可不填
     */
    @Schema(description = "乡镇， 可不填")
    private String town;


}