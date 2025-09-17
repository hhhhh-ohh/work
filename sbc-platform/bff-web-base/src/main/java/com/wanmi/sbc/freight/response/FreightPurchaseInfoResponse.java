package com.wanmi.sbc.freight.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wur
 * @className FreightPurchaseInfoResponse
 * @description TODO
 * @date 2022/7/14 9:10
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreightPurchaseInfoResponse {

    @Schema(description = "目标SKUId")
    private List<String> skuIdList;
}