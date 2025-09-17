package com.wanmi.ares.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xuyunpeng
 * @className PayMemberOverViewResponse
 * @description
 * @date 2022/5/25 3:52 PM
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PayMemberOverViewResponse implements Serializable {
    private static final long serialVersionUID = -7319636402156132987L;

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
}
