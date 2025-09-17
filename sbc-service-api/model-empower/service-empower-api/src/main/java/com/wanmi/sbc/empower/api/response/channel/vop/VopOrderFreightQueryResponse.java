package com.wanmi.sbc.empower.api.response.channel.vop;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description 订单查询运费
 * @author daiyitian
 * @date 2021/5/10 17:13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VopOrderFreightQueryResponse implements Serializable {

    private static final long serialVersionUID = -7665203890536521664L;

    @Schema(description = "总运费")
    private BigDecimal freight;

    @Schema(description = "基础运费")
    private BigDecimal baseFreight;

    @Schema(description = "偏远地区加收运费")
    private BigDecimal remoteRegionFreight;

    @Schema(description = "续重运费")
    private BigDecimal conFreight;
}
