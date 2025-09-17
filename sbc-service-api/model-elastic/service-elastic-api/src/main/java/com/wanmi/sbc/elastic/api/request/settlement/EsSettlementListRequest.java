package com.wanmi.sbc.elastic.api.request.settlement;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.elastic.bean.dto.settlement.EsSettlementDTO;
import com.wanmi.sbc.elastic.bean.dto.settlement.LakalaEsSettlementDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author yangzhen
 * @Description //结算单信息
 * @Date 14:29 2020/12/11
 * @Param
 * @return
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EsSettlementListRequest extends BaseRequest {


    /**
     * 结算信息批量
     **/
    @Schema(description = "结算信息批量数据")
    private List<EsSettlementDTO> lists;

    @Schema(description = "拉卡拉结算信息批量数据")
    private List<LakalaEsSettlementDTO> lakalaLists;

}
