package com.wanmi.sbc.empower.api.response.ledgercontent;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.empower.bean.vo.LedgerContentVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>拉卡拉经营内容表分页结果</p>
 * @author zhanghao
 * @date 2022-07-08 11:02:05
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerContentPageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 拉卡拉经营内容表分页结果
     */
    @Schema(description = "拉卡拉经营内容表分页结果")
    private MicroServicePage<LedgerContentVO> ledgerContentVOPage;
}
