package com.wanmi.sbc.customer.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author edz
 * @className LakalaDistributionBindVO
 * @description TODO
 * @date 2022/9/14 15:27
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class LakalaDistributionBindVO implements Serializable {
    @Schema(description = "ledger_account主键")
    private String id;

    @Schema(description = "分销员名称")
    private String distributionName;

    @Schema(description = "分销员账号")
    private String distributionAccount;

    @Schema(description = "分账关系绑定状态")
    private Integer ledgerBindState;
}
