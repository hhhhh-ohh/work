package com.wanmi.sbc.order.api.request.settlement;

import com.wanmi.sbc.account.bean.dto.SettlementDTO;
import com.wanmi.sbc.order.api.request.OrderBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.OutputStream;

/**
 * <p>导出单条结算明细request</p>
 * Created by of628-wenzhi on 2018-10-13-下午6:29.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettlementDetailExportRequest extends OrderBaseRequest {

    private static final long serialVersionUID = 6806492461429410935L;

    /**
     * 结算明细查询参数
     */
    @Schema(description = "结算明细查询参数")
    private SettlementDTO settlementDTO;

    /**
     * 文件流
     */
    @Schema(description = "文件流")
    private OutputStream outputStream;
}
