package com.wanmi.sbc.vas.api.response.channel;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 渠道订单验证响应类
 * @author daiyitian
 * @date 2021/5/10 17:14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ChannelOrderVerifyResponse extends BasicResponse {

    private static final long serialVersionUID = -8015726253741444133L;

    @Schema(description = "需要下架商品id")
    private List<String> offAddedSkuId = new ArrayList<>();

    @Schema(description = "是否含有失效商品")
    private Boolean invalidGoods = Boolean.FALSE;

    @Schema(description = "是否含有区域限购商品")
    private Boolean noAuthGoods = Boolean.FALSE;

    @Schema(description = "是否含有缺货商品")
    private Boolean noOutStockGoods = Boolean.FALSE;
}
