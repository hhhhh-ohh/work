package com.wanmi.sbc.empower.api.response.ledgercontent;

import com.wanmi.sbc.empower.bean.vo.LedgerContentVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）拉卡拉经营内容表信息response</p>
 * @author zhanghao
 * @date 2022-07-08 11:02:05
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerContentByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 拉卡拉经营内容表信息
     */
    @Schema(description = "拉卡拉经营内容表信息")
    private LedgerContentVO ledgerContentVO;
}
