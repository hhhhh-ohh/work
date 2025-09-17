package com.wanmi.sbc.goods.api.request.contract;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.enums.CheckState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 二次签约信息审核请求结构
 * Created by wangchao on 2017/11/2.
 */
@Schema
@Data
public class ContractAuditCheckRequest extends BaseRequest {

    private static final long serialVersionUID = -8451929163613807743L;

    /**
     * 二次签约审核状态
     */
    @Schema(description = "二次签约审核状态")
    private CheckState contractAuditState;

    /**
     * 二次签约拒绝原因
     */
    @Schema(description = "二次签约拒绝原因")
    private String contractAuditReason;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

}
