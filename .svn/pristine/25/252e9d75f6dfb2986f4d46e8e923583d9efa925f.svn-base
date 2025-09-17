package com.wanmi.sbc.account.api.request.finance.record;

import com.wanmi.sbc.account.bean.enums.SettleStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * <p>批量修改结算单状态request</p>
 * Created by daiyitian on 2018-10-13-下午6:29.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettlementBatchModifyStatusRequest extends AccountBaseRequest {

    private static final long serialVersionUID = 4865353991146144198L;
    /**
     * 多个结算编号
     */
    @Schema(description = "多个结算编号")
    @NotEmpty
    private List<Long> settleIdList;

    /**
     * 结算状态 {@link SettleStatus}
     */
    @Schema(description = "结算状态")
    @NotNull
    private SettleStatus status;

}
