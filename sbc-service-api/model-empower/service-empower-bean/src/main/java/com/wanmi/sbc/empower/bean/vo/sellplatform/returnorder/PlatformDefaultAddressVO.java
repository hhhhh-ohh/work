package com.wanmi.sbc.empower.bean.vo.sellplatform.returnorder;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wur
 * @className ChannelsCheckAccessInfoVO
 * @description 审核状态
 * @date 2022/4/1 19:52
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformDefaultAddressVO implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 收件人姓名
     */
    @Schema(description = "收件人姓名")
    private String receiver_name;

    /**
     * 详细收货地址信息
     */
    @Schema(description = "详细收货地址信息")
    private String detailed_address;

    /**
     * 收件人手机号码
     */
    @Schema(description = "收件人手机号码")
    private String tel_number;

    @Schema(description = "国家")
    private String country;

    @Schema(description = "省份")
    private String province;

    @Schema(description = "城市")
    private String city;

    @Schema(description = "乡镇")
    private String town;

}