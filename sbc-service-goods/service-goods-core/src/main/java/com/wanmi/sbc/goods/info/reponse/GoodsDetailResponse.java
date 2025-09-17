package com.wanmi.sbc.goods.info.reponse;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsPropDetailRelVO;
import lombok.Data;

import java.util.List;

/**
 * @discription 商品图文信息+属性response
 * @author yangzhen
 * @date 2020/9/3 11:14
 * @param
 * @return
 */
@Data
public class GoodsDetailResponse extends BasicResponse {

    /**
     * 商品图文信息
     */
    private String goodsDetail;

    /**
     * 商品属性列表
     */
    private List<GoodsPropDetailRelVO> goodsPropDetailRels;
}
