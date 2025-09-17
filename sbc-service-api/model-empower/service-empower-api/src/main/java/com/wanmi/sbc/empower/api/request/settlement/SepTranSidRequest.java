package com.wanmi.sbc.empower.api.request.settlement;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author edz
 * @className sepTranSidRequest
 * @description 分账查询
 * @date 2022/7/30 16:57
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class SepTranSidRequest implements Serializable {

    @Schema(description = "商家ID")
    private String supplierComPanyInfoId;

    @Schema(description = "拉卡拉分账流水号")
    private List<String> sepTranSids;

}
