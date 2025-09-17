package com.wanmi.sbc.empower.api.response.ledgermcc;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.empower.bean.vo.LedgerMccVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>拉卡拉mcc表分页结果</p>
 * @author zhanghao
 * @date 2022-07-08 11:01:18
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerMccPageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 拉卡拉mcc表分页结果
     */
    @Schema(description = "拉卡拉mcc表分页结果")
    private MicroServicePage<LedgerMccVO> ledgerMccVOPage;
}
