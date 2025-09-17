package com.wanmi.ares.response;

import com.wanmi.ares.view.paymember.PayMemberAreaView;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author xuyunpeng
 * @className PayMemberAreaViewResponse
 * @description
 * @date 2022/5/25 2:28 PM
 **/
@Data
@Schema
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayMemberAreaViewResponse implements Serializable {
    private static final long serialVersionUID = -8255735818509302716L;

    /**
     * 付费会员总数
     */
    @Schema(description = "付费会员总数")
    private Long total;

    /**
     * 占比
     */
    @Schema(description = "占比")
    private BigDecimal proportion;

    /**
     * 付费会员区域数据
     */
    @Schema(description = "付费会员区域数据")
    private List<PayMemberAreaView> areaViews;
}
