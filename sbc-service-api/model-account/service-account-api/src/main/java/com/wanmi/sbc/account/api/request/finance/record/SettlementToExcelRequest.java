package com.wanmi.sbc.account.api.request.finance.record;

import com.wanmi.sbc.account.bean.enums.SettleStatus;
import com.wanmi.sbc.common.enums.StoreType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 财务结算导出参数结构
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class SettlementToExcelRequest extends BasePageRequest {

    private static final long serialVersionUID = 5301883018355841384L;
    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    private String startTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private String endTime;

    /**
     * 店铺Id
     */
    @Schema(description = "店铺Id")
    private Long storeId;

    /**
     * 结算状态 {@link SettleStatus}
     */
    @Schema(description = "结算状态")
    private SettleStatus settleStatus;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 批量店铺ID
     */
    @Schema(description = "批量店铺ID")
    private List<Long> storeListId;

    /**
     * 商家类型 0供应商 1商家
     */
    private StoreType storeType;

    /**
     * 结算单Id
     */
    @Schema(description = "结算单Id")
    private List<Long> idList;

}
