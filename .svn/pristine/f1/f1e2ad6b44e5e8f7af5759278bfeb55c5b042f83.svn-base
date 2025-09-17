package com.wanmi.sbc.order.settlement.model.root;

import com.wanmi.sbc.account.bean.enums.LakalaLedgerStatus;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.order.settlement.model.value.LakalaSettleGood;
import com.wanmi.sbc.order.settlement.model.value.LakalaSettleTrade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author edz
 * @className LakalaSettlementDetail
 * @description TODO
 * @date 2022/7/15 10:16
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LakalaSettlementDetail {
    /**
     * 主键
     */
    private String id;

    /**
     * 结算明细Id
     */
    private String settleUuid;

    /**
     * 结算单明细商家类型
     */
    private StoreType storeType;

    /**
     * 账期开始时间
     */
    private String startTime;

    /**
     * 账期结束时间
     */
    private String endTime;

    /**
     * 店铺Id
     */
    private Long storeId;

    /**
     * 供应商id
     */
    private Long providerId;

    /**
     * 是否特价
     */
    private boolean isSpecial;

    /**
     * 订单信息
     */
    private LakalaSettleTrade settleTrade;

    /**
     * 订单商品信息
     */
    private List<LakalaSettleGood> settleGoodList;

    /**
     * 订单和退单是否属于同一个账期
     */
    private boolean tradeAndReturnInSameSettle;

    /**
     * 分账状态
     */
    private LakalaLedgerStatus lakalaLedgerStatus;

    /**
     * 拉卡拉分账流水号
     */
    private String sepTranSid;

    /**
     * 拉卡拉费率
     */
    private String lakalaRate;

    /**
     * 拉卡拉手续费
     */
    private String lakalaHandlingFee;

    /**
     * 代销商家的Id
     */
    private Long supplierStoreId;

    /**
     * 分账失败原因
     */
    private String lakalaLedgerFailReason;
}
