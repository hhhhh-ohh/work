package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.DistributeChannel;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author liguang
 * @description 查询订单快照
 * @date 2022/4/1 16:02
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindTradeSnapshotRequest implements Serializable {

    @Schema(description = "会员ID")
    @NotNull
    private String customerId;

    @Schema(description = "用户终端的token")
    @NotNull
    private String terminalToken;

    @Schema(description = "分销渠道")
    private DistributeChannel distributeChannel;

    @Schema(description = "是否购买了企业会员增值服务")
    private Boolean vasIpeFlag;
}
