package com.wanmi.sbc.order.api.response.purchase;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.PurchaseMarketingCalcVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-12-03
 */
@Data
@Schema
public class PurchaseGetStoreMarketingResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    @Schema(description = "店铺营销信息map,key为店铺id，value为营销信息列表")
    private HashMap<Long, List<PurchaseMarketingCalcVO>> map;
}
