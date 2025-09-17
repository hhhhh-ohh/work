package com.wanmi.sbc.elastic.api.response.settlement;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.elastic.bean.vo.settlement.EsSettlementVO;
import com.wanmi.sbc.elastic.bean.vo.settlement.LakalaEsSettlementVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * @Author yangzhen
 * @Description // 结算单
 * @Date 14:42 2020/12/11
 * @Param
 * @return
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class EsSettlementResponse extends BasicResponse {

    /**
     * 索引SKU
     */
    @Schema(description = "索引SKU")
    private MicroServicePage<EsSettlementVO> esSettlementVOPage = new MicroServicePage<>(new ArrayList<>());
    @Schema(description = "拉卡拉结算单")
    private MicroServicePage<LakalaEsSettlementVO> lakalaEsSettlementVOPage = new MicroServicePage<>(new ArrayList<>());

}
