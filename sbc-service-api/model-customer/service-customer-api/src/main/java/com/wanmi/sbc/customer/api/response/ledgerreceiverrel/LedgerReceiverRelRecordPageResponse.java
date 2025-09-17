package com.wanmi.sbc.customer.api.response.ledgerreceiverrel;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.LedgerReceiverRelRecordVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>分账绑定关系补偿记录分页结果</p>
 * @author xuyunpeng
 * @date 2022-07-14 15:15:45
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerReceiverRelRecordPageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 分账绑定关系补偿记录分页结果
     */
    @Schema(description = "分账绑定关系补偿记录分页结果")
    private MicroServicePage<LedgerReceiverRelRecordVO> ledgerReceiverRelRecordVOPage;
}
