package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.order.bean.dto.TradeQueryDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @className CountByNotWrittenOffOrderRequest
 * @description TODO
 * @author 黄昭
 * @date 2021/9/8 17:15
 **/
@Data
@Builder
@Schema
@NoArgsConstructor
@AllArgsConstructor
public class CountByNotWrittenOffOrderRequest extends BaseRequest {

    private static final long serialVersionUID = 6607831037370920417L;

    /**
     * 是否门店主账号
     */
    @Schema(description = "是否门店主账号")
    private Boolean isMasterAccount = false;

    /**
     * 商家Id
     */
    @Schema(description = "商家Id")
    private Long storeId;

    /**
     * 关联自提点Id
     */
    @Schema(description = "关联自提点Id")
    private List<Long> pickupIds;

    /**
     * 未绑定员工自提点
     */
    @Schema(description = "未绑定员工自提点")
    private List<Long> noEmployeePickupIds;
}
