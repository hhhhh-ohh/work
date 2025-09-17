package com.wanmi.sbc.elastic.api.request.settlement;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.SettleStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author yangzhen
 * @Description // 结算单查询
 * @Date 18:30 2020/12/7
 * @Param
 * @return
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class SettlementQueryRequest extends BaseRequest {

    /**
     * 结算单号id列表
     */
    @Schema(description = "结算单号id列表")
    private List<Long> settleIdLists;

    /**
     * 结算状态 {@link SettleStatus}
     */
    @Schema(description = "结算状态")
    @NotNull
    private Integer status;
}
