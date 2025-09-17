package com.wanmi.sbc.empower.api.request.channel.linkedmall;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class LinkedMallCateChainByGomallodsIdRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "商品id")
    private Long goodsId;
}
