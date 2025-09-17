package com.wanmi.sbc.order.api.response.orderperformance;

import com.wanmi.sbc.common.base.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetSchooluUniformSalesDataResponse extends BasicResponse {

    @Schema(description = "夏装数量")
    private Long summerNum = 0L;

    @Schema(description = "秋装数量")
    private Long autumnNum = 0L;

    @Schema(description = "冬装数量")
    private Long winterNum = 0L;

    @Schema(description = "总套数量")
    private Long totalNum = 0L;

    @Schema(description = "夏装佣金")
    private BigDecimal summerCommission= BigDecimal.ZERO;

    @Schema(description = "秋装佣金")
    private BigDecimal autumnCommission= BigDecimal.ZERO;

    @Schema(description = "冬装佣金")
    private BigDecimal winterCommission= BigDecimal.ZERO;

    @Schema(description = "总佣金")
    private BigDecimal totalCommission= BigDecimal.ZERO;

    @Schema(description = "代理商唯一码")
    private String agentUniqueCode;

    @Schema(description = "秋装营业额")
    private BigDecimal autumnSaleAmount;

    @Schema(description = "夏装营业额")
    private BigDecimal summerSaleAmount;

    @Schema(description = "冬装营业额")
    private BigDecimal winterSaleAmount;

    @Schema(description = "总营业额")
    private BigDecimal totalSaleAmount;

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
    private List<GetSchooluUniformSalesDataResponse> childList = new ArrayList<>();

    public void add(String season, Long quantity, BigDecimal commission) {
        if ("春秋装".equals(season)) {
            this.autumnNum += quantity;
            this.autumnCommission = this.autumnCommission.add(commission);
        } else if ("夏装".equals(season)) {
            this.summerNum += quantity;
            this.summerCommission = this.summerCommission.add(commission);
        } else if ("冬装".equals(season)) {
            this.winterNum += quantity;
            this.winterCommission = this.winterCommission.add(commission);
        }
        this.totalNum += quantity;
        this.totalCommission = this.totalCommission.add(commission);
    }

}
