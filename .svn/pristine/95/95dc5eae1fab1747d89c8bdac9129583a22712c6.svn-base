package com.wanmi.sbc.order.api.request.settlement;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.customer.bean.vo.LedgerAccountVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-10 14:21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class SettlementAnalyseRequest extends BaseRequest {

    /**
     * 结算定时任务参数
     */
    @Schema(description = "结算定时任务参数")
    private String param;

    /**
     * 结算类型
     */
    @Schema(description = "结算类型")
    private StoreType storeType;

    LedgerAccountVO ledgerAccountVO;

    StoreVO store;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    String endTime;

}
