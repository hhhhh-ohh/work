package com.wanmi.sbc.order.api.response.purchase;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.PurchaseVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-12-03
 */
@Data
@Schema
public class PurchaseQueryResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    @Schema(description = "采购单列表")
    private List<PurchaseVO> purchaseList = new ArrayList<>();
}
