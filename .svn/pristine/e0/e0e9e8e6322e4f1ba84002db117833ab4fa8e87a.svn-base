package com.wanmi.sbc.empower.api.response.channel.goods;

import com.wanmi.sbc.common.enums.BoolFlag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author EDZ
 * @className CheckStuStateResponse
 * @description 校验SKU可售性统一接口返回
 * @date 2021/5/20 19:19
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelCheckStuStateResponse implements Serializable{

    @Schema(description = "渠道商品SKUID")
    private String skuId;

    @Schema(description = "是否可售-不可售=下架")
    private BoolFlag isSale;
}
