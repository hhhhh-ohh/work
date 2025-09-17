package com.wanmi.sbc.empower.api.request.Ledger.lakala;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 费率信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeeData {
        /**
         * 银行账户方编码
         */
        String issuerTypeId;

        /**
         * 费率类型
         */
        String feeRateTypeCode;

        /**
         * 费率类型名称
         */
        String feeRateTypeName;


        /**
         * 手续费率(%)
         */
        String feeRatePct;

        /**
         * 单笔交易手续费封顶
         */
        String feeUpperAmtPcnt;

        String feeLowerAmtPcnt;

        public FeeData(String feeRateTypeCode, String feeRateTypeName, String feeRatePct, String feeUpperAmtPcnt) {
                this.feeRateTypeCode = feeRateTypeCode;
                this.feeRateTypeName = feeRateTypeName;
                this.feeRatePct = feeRatePct;
                this.feeUpperAmtPcnt = feeUpperAmtPcnt;
        }

}
