package com.wanmi.sbc.goods.api.request.goodsevaluate;

import com.wanmi.sbc.common.base.BaseRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 批量保存商品评论参数
 * @author lvzhenwei
 * @date 15:35 2019/4/10
 **/
@Data
public class GoodsEvaluateAddListRequest extends BaseRequest {

    private static final long serialVersionUID = 3080316161150287719L;

    private List<GoodsEvaluateAddRequest> goodsEvaluateAddList;
}
