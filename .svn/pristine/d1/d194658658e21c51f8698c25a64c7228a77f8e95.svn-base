package com.wanmi.sbc.order.api.response.settlement;

import com.wanmi.sbc.account.bean.enums.LakalaLedgerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @author edz
 * @className LakalaLedgerStatusResponse
 * @description 分账列表操作按钮的展示
 * @date 2022/7/30 16:16
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LakalaLedgerStatusResponse implements Serializable {

    // <结算单ID，分账按钮状态>
    private Map<String, LakalaLedgerStatus> operateMap;

    // <结算单ID，分账按钮状态>
    private Map<String, LakalaLedgerStatus> statusMap;
}
