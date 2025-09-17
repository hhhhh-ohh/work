package com.wanmi.sbc.marketing.api.response.electroniccoupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.enums.CardSaleState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @author xuyunpeng
 * @className ElectronicSendRecordNumResponse
 * @description
 * @date 2022/2/10 1:45 下午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicSendRecordNumResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 商品关联卡券的发放次数
     */
    @Schema(description = "商品关联卡券的发放次数")
    private Map<String, Long> sendMap;

    /**
     * 卡券销售状态
     */
    @Schema(description = "卡券销售状态")
    private Map<Long, CardSaleState> saleStateMap;

}
