package com.wanmi.sbc.empower.api.request.channel.linkedmall;


import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.empower.bean.dto.channel.base.ChannelGoodsBuyNumDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * \* User: yhy
 * \* Date: 2020-8-10
 * \* Time: 17:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class LinkedMallRenderOrderRequest implements Serializable {

    @Schema(description = "商品信息")
    @NotEmpty
    private List<ChannelGoodsBuyNumDTO> lmGoodsItems;

    @Schema(description = "街道上一级的divisionCode")
    @NotBlank
    private String divisionCode;

    @Schema(description = "收货人姓名")
    @NotBlank
    private String fullName;

    @Schema(description = "收货人电话")
    @NotBlank
    private String mobile;

    @Schema(description = "收货人地址,详细地址需补足5个字符")
    @NotBlank
    private String addressDetail;

    @Schema(description = "商城内部用户id")
    @NotBlank
    private String bizUid;

    /**
     * 构建收货地址信息
     * linkedMall需要 json字符串 收货人地址大于等于5个字符
     * { "divisionCode": "街道上一级的divisionCode", "fullName": "收货人姓名", "mobile": "收货人电话", "addressDetail": "收货人地址" }
     */
    public String buildDeliveryAddress() {
        Map<String, String> map = new HashMap<>();
        // 街道上一级的divisionCode
        if (StringUtils.isNotBlank(divisionCode)) {
            map.put("divisionCode", divisionCode);
        }
        // 收货人姓名
        if (StringUtils.isNotBlank(fullName)) {
            map.put("fullName", fullName);
        }
        // 收货人电话
        if (StringUtils.isNotBlank(mobile)) {
            map.put("mobile", mobile);
        }
        // 收货人地址,详细地址需补足5个字符
        if (StringUtils.isNotBlank(addressDetail)) {
            if (addressDetail.length() < Constants.FIVE) {
                addressDetail = String.format("%-5s", addressDetail);
            }
            map.put("addressDetail", addressDetail);
        }
        return JSON.toJSONString(map);
    }

}
