package com.wanmi.sbc.customer.api.response.ledgerrecord;

import com.wanmi.sbc.customer.bean.vo.LedgerRecordVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）分账开通记录信息response</p>
 * @author 许云鹏
 * @date 2022-07-01 16:36:53
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerRecordByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 分账开通记录信息
     */
    @Schema(description = "分账开通记录信息")
    private LedgerRecordVO ledgerRecordVO;
}
