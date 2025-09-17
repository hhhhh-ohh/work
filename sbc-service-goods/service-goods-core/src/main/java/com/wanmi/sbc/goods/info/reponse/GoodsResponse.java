package com.wanmi.sbc.goods.info.reponse;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsSaveVO;
import lombok.Data;

/**
 * 商品视图响应
 * Created by daiyitian on 2017/3/24.
 */
@Data
public class GoodsResponse extends BasicResponse {

    /**
     * 商品信息
     */
    private GoodsSaveVO goods;
}
