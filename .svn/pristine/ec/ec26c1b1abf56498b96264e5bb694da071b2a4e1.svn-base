package com.wanmi.sbc.customer.api.request.ledgersupplier;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.customer.bean.enums.LedgerAccountState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author edz
 * @className DistributionApplyRecordRequest
 * @description TODO
 * @date 2022/9/6 18:05
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class DistributionApplyRecordRequest extends BaseQueryRequest implements Serializable {

    @Schema(description = "分销员账号")
    private String distributionAccount;

    @Schema(description = "分销员名称")
    private String distributionName;

    @Schema(description = "分销员等级")
    private String distributionLevelId;

    @Schema(description = "进件状态")
    private LedgerAccountState applyState;

}
