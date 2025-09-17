package com.wanmi.sbc.customer.api.response.ledgerrecord;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.LedgerRecordVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>分账开通记录分页结果</p>
 * @author 许云鹏
 * @date 2022-07-01 16:36:53
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerRecordPageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 分账开通记录分页结果
     */
    @Schema(description = "分账开通记录分页结果")
    private MicroServicePage<LedgerRecordVO> ledgerRecordVOPage;
}
