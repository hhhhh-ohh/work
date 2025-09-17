package com.wanmi.sbc.order.api.response.trade;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-04 11:02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class ValidateTradeAndAccountResponse implements Serializable {

    /**
     * 账户内是否存在未完成的订单/退单（包括未过售后期）条数
     */
    @Schema(description = "账户内是否存在未完成的订单/退单（包括未过售后期）条数")
    private Long count;

    /**
     * 账户内是否存在未完成的积分订单
     */
    @Schema(description = "账户内是否存在未完成的积分订单条数")
    private Long pointCount;

    /**
     * 余额内是否已清空 true 有余额 false没余额
     */
    @Schema(description = "余额内是否已清空")
    private boolean hasBalance;

    /**
     * 授信账号内是否有欠款 true 有欠款 false没欠款
     */
    @Schema(description = "授信账号内是否有欠款")
    private boolean hasCreditArrears;

    /**
     * 付费会员是否在有效期内
     */
    @Schema(description = "付费会员是否在有效期内")
    private boolean payMemberActive;

}
