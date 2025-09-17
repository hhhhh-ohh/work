package com.wanmi.sbc.order.api.request.purchase;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p></p>
 * @author: sunkun
 * Date: 2018-12-06
 */
@Data
@AllArgsConstructor
@Schema
public class PurchaseGetGoodsMarketingRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    public PurchaseGetGoodsMarketingRequest(){

    }

    public PurchaseGetGoodsMarketingRequest(List<GoodsInfoVO> goodsInfos, CustomerVO customer){
        this.goodsInfos = goodsInfos;
        this.customer = customer;
    }

    @Schema(description = "商品信息")
    private List<GoodsInfoVO> goodsInfos;

    @Schema(description = "客户信息")
    private CustomerVO customer;

    @Schema(description = "门店ID")
    private Long storeId;
}
