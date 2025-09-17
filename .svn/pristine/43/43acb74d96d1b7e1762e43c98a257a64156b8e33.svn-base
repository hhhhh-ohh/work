package com.wanmi.sbc.order.api.response.settlement;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.SettlementDetailVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>根据条件查询单条结算明细返回结构</p>
 * Created by of628-wenzhi on 2018-10-13-下午7:02.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettlementDetailByParamResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 结算明细 {@link SettlementDetailVO}
     */
    @Schema(description = "结算明细")
    private SettlementDetailVO settlementDetailVO;
}
