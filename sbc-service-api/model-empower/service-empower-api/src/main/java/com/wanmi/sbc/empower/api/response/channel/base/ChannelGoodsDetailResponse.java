package com.wanmi.sbc.empower.api.response.channel.base;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/***
 * 渠道商品详情查询响应类
 * @author zhengyang
 * @date 2021/06/07 17:14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ChannelGoodsDetailResponse implements Serializable {

    private static final long serialVersionUID = -1L;

    @Schema(description = "商品详情")
    private String goodsDetail;
}
