package com.wanmi.sbc.order.api.request.purchase;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.customer.bean.dto.CustomerDTO;
import com.wanmi.sbc.order.bean.dto.PurchaseMergeDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-09-25
 */
@Data
@Schema
public class PurchaseMergeRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "采购单信息")
    @NotNull
    @Size(min = 1)
    @Valid
    private List<PurchaseMergeDTO> purchaseMergeDTOList;

    @Schema(description = "客户信息")
    private CustomerDTO customer;

    /**
     * 邀请人id-会员id
     */
    @Schema(description = "邀请人id")
    String inviteeId;

    /**
     * 终端来源
     */
    @Schema(description = "终端来源", hidden = true)
    private TerminalSource terminalSource;
}
