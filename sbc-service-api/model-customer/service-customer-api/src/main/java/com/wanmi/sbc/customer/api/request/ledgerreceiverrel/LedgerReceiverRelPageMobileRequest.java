package com.wanmi.sbc.customer.api.request.ledgerreceiverrel;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @author xuyunpeng
 * @className LedgerReceiverRelPageMobileRequest
 * @description
 * @date 2022/9/14 10:10 AM
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerReceiverRelPageMobileRequest extends BaseQueryRequest {
    private static final long serialVersionUID = -2175756871264731182L;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 状态：0、全部店铺 1、审核中 2、审核失败 3、绑定中 4、已绑定 5、绑定失败
     */
    @Schema(description = "状态：0、全部店铺 1、审核中 2、审核失败 3、绑定中 4、已绑定 5、绑定失败")
    private Integer status;

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private String customerId;
}
