package com.wanmi.sbc.order.api.request.settlement;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * @author edz
 * @className LakalaSettlementDetailByIdReq
 * @description TODO
 * @date 2022/9/24 16:41
 **/
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LakalaSettlementDetailByIdReq implements Serializable {

    @Schema(description = "结算单详情ID")
    private String id;
}
