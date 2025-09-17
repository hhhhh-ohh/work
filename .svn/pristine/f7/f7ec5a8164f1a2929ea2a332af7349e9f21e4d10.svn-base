package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author edz
 * @className DistributionApplyRecordVO
 * @description TODO
 * @date 2022/9/7 17:43
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class DistributionApplyRecordVO extends BaseQueryRequest {
    
    @Schema(description = "分销员账号")
    private String customerAccount;

    @Schema(description = "分销员账号")
    private String customerName;

    @Schema(description = "分销员等级")
    private String distributorLevelName;

    @Schema(description = "进件状态")
    private Integer state;

    @Schema(description = "进件时间")
    private String passTime;
}
