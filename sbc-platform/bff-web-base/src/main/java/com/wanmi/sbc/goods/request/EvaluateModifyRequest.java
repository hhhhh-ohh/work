package com.wanmi.sbc.goods.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.api.request.goodsevaluate.GoodsEvaluateModifyRequest;
import com.wanmi.sbc.goods.api.request.goodsevaluateimage.GoodsEvaluateImageAddRequest;
import com.wanmi.sbc.goods.api.request.goodsevaluateimage.GoodsEvaluateImageModifyRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @Auther: jiaojiao
 * @Date: 2019/3/7 09:56
 * @Description:
 */

@Schema
@Data
public class EvaluateModifyRequest extends BaseRequest {

    private GoodsEvaluateModifyRequest goodsEvaluateModifyRequest;

    private List<GoodsEvaluateImageAddRequest> goodsEvaluateImageAddRequestList;

}
