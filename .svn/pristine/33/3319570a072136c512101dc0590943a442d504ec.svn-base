package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.customer.bean.dto.CompanyInfoDTO;
import com.wanmi.sbc.customer.bean.dto.StoreInfoDTO;
import com.wanmi.sbc.order.bean.dto.TradeCreateDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-04 15:36
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class TradeWrapperBackendCommitRequest extends BaseRequest {

    /**
     * 操作人
     */
    @Schema(description = "操作人")
    private Operator operator;

    /**
     * 商家信息
     */
    @Schema(description = "商家信息")
    private CompanyInfoDTO companyInfo;

    /**
     * 店铺信息
     */
    @Schema(description = "店铺信息")
    private StoreInfoDTO storeInfo;

    /**
     * 订单信息
     */
    @Schema(description = "订单信息")
    private TradeCreateDTO tradeCreate;

    /**
     * 是否开启第三方平台
     */
    @Schema(description = "是否开启第三方平台")
    private Boolean isOpen;

}
