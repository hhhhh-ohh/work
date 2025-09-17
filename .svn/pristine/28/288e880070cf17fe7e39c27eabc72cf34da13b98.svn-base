package com.wanmi.sbc.open.response;

import com.wanmi.sbc.common.base.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrmSchooluUniformSalesDataResponse extends BasicResponse {

    @Schema(description = "夏装数量")
    private Integer summerNum = 0;

    @Schema(description = "秋装数量")
    private Integer autumnNum  = 0;

    @Schema(description = "冬装数量")
    private Integer winterNum  = 0;

    @Schema(description = "总套数量")
    private Integer totalNum  = 0;

    @Schema(description = "夏装佣金")
    private BigDecimal summerCommission = BigDecimal.ZERO;

    @Schema(description = "秋装佣金")
    private BigDecimal autumnCommission= BigDecimal.ZERO;

    @Schema(description = "冬装佣金")
    private BigDecimal winterCommission= BigDecimal.ZERO;

    @Schema(description = "总佣金")
    private BigDecimal totalCommission= BigDecimal.ZERO;

    @Schema(description = "市级id")
    private Long cityId;

    @Schema(description = "市级名称")
    private String cityName;

    @Schema(description = "区级id")
    private Long areaId;

    @Schema(description = "区级名称")
    private String areaName;

    @Schema(description = "门店名称")
    private String shopName;

    @Schema(description = "列表数据")
    private List<CrmSchooluUniformSalesDataResponse> childList;

}
