package com.wanmi.sbc.empower.api.response.channel.logistics;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class LogisticsLinkedMallResponse {

    @Schema(description = "物流信息")
    private List<Map<String,String>>  logisticsDetailList;

    @Schema(description = "物流公司标准编码")
    private String logisticStandardCode;

    @Schema(description = "物流号")
    private String logisticNo;

    @Schema(description = "物流公司名称")
    private String logisticCompanyName;

    @Schema(description = "发货日期")
    private String deliveryTime;
}
