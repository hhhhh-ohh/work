package com.wanmi.sbc.order.api.response.settlement;

import com.wanmi.sbc.order.bean.vo.LakalaSettlementDetailVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author edz
 * @className LakalaSettlementDetailByIdResp
 * @description TODO
 * @date 2022/9/24 16:45
 **/
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LakalaSettlementDetailByIdResp implements Serializable {

    @Schema(description = "拉卡拉结算单详情")
    private LakalaSettlementDetailVO settlementDetailVO;
}
