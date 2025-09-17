package com.wanmi.sbc.account.api.request.finance.record;

import com.wanmi.sbc.account.bean.enums.SettleStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 结算统计查询请求
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class SettlementCountRequest extends BasePageRequest {

    private static final long serialVersionUID = 3745014890620972400L;
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

}
