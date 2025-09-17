package com.wanmi.sbc.marketing.api.request.countprice;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.dto.CountPriceDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @description   商品算价请求类
 * @author  wur
 * @date: 2022/2/23 16:31
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeCountMarketingPriceRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description ="用户编号")
    private String customerId;

    @Schema(description ="是否强制下单，true：任何异常都以无营销处理")
    private Boolean forceCommit = Boolean.FALSE;

    @Schema(description ="用户未选择时默认匹配营销活动标识")
    private Boolean defaultMate = Boolean.FALSE;

    /**
     * 订单快照信息
     */
    @Schema(description = "订单快照信息")
    private List<CountPriceDTO> countPriceVOList;
}