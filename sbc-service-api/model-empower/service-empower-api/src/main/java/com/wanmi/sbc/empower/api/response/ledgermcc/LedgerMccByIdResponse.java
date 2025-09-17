package com.wanmi.sbc.empower.api.response.ledgermcc;

import com.wanmi.sbc.empower.bean.vo.LedgerMccVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）拉卡拉mcc表信息response</p>
 * @author zhanghao
 * @date 2022-07-08 11:01:18
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerMccByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 拉卡拉mcc表信息
     */
    @Schema(description = "拉卡拉mcc表信息")
    private LedgerMccVO ledgerMccVO;
}
