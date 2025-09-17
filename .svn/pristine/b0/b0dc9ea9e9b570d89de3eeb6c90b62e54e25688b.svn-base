package com.wanmi.sbc.elastic.api.response.ledger;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.elastic.bean.vo.ledger.EsLedgerBindInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyunpeng
 * @className EsLedgerBindInfoPageResponse
 * @description
 * @date 2022/7/13 4:14 PM
 **/
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsLedgerBindInfoPageResponse extends BasicResponse {

    private static final long serialVersionUID = -4513849091322897706L;

    @Schema(description = "分页数据")
    private MicroServicePage<EsLedgerBindInfoVO> ledgerBindInfoVOPage;
}
