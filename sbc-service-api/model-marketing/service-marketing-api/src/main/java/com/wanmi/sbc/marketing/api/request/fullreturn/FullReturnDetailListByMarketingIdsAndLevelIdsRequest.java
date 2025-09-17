package com.wanmi.sbc.marketing.api.request.fullreturn;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description 根据营销id和等级id批量查询赠品明细的请求类
 * @author xufeng
 * @date 2022/4/11 17:26
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullReturnDetailListByMarketingIdsAndLevelIdsRequest implements Serializable {

    private static final long serialVersionUID = 6408251823379795491L;

    @NotEmpty
    @Schema(description = "营销Id")
    private List<Long> marketingIds;

    @NotEmpty
    @Schema(description = "满赠等级id")
    private List<Long> returnLevelIds;
}
