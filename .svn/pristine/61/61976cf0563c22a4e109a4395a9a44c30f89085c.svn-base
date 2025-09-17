package com.wanmi.sbc.order.api.request.purchase;

import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.customer.bean.dto.CustomerDTO;
import com.wanmi.sbc.order.bean.dto.PurchaseQueryDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-12-03
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema
public class PurchaseMiniListRequest extends PurchaseQueryDTO {

    private static final long serialVersionUID = 6755875113792084803L;

    @Schema(description = "客户信息")
    private CustomerDTO customer;

    /**
     * 是否是pc端访问或者社交分销关闭
     */
    @Schema(description = "是否是pc端访问或者社交分销关闭")
    private Boolean pcAndNoOpenFlag;

    /**
     * 终端类型
     */
    @Schema(description = "终端类型")
    private TerminalSource terminalSource;

}
