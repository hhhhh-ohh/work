package com.wanmi.sbc.customer.api.response.ledgerrecord;

import com.wanmi.sbc.customer.bean.vo.LedgerRecordVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>分账开通记录新增结果</p>
 * @author 许云鹏
 * @date 2022-07-01 16:36:53
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerRecordAddResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的分账开通记录信息
     */
    @Schema(description = "已新增的分账开通记录信息")
    private LedgerRecordVO ledgerRecordVO;
}
