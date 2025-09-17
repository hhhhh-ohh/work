package com.wanmi.sbc.goods.api.request.goodsevaluate;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.StoreEvaluateVO;
import com.wanmi.sbc.goods.bean.vo.GoodsEvaluateVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author houshuai
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsEvaluateAddResponse extends BasicResponse {

    private static final long serialVersionUID = 5487922117455719886L;

    /**
     * 已新增的店铺评价信息
     */
    private StoreEvaluateVO storeEvaluateVO;

    /**
     * 商品评价
     */
    private GoodsEvaluateVO goodsEvaluateVO;
}