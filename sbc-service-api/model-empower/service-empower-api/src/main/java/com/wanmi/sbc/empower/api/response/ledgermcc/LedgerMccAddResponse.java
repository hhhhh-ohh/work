package com.wanmi.sbc.empower.api.response.ledgermcc;

import com.wanmi.sbc.empower.bean.vo.LedgerMccVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>拉卡拉mcc表新增结果</p>
 * @author zhanghao
 * @date 2022-07-08 11:01:18
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerMccAddResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的拉卡拉mcc表信息
     */
    @Schema(description = "已新增的拉卡拉mcc表信息")
    private LedgerMccVO ledgerMccVO;
}
