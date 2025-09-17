package com.wanmi.sbc.goods.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.api.request.storeevaluate.StoreEvaluateAddRequest;
import com.wanmi.sbc.goods.api.request.goodsevaluate.GoodsEvaluateAddRequest;
import com.wanmi.sbc.goods.api.request.goodsevaluateimage.GoodsEvaluateImageAddRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import jakarta.validation.Valid;
import java.util.List;

/**
 * @Auther: jiaojiao
 * @Date: 2019/3/7 09:56
 * @Description:
 */

@Schema
@Data
public class EvaluateAddRequest extends BaseRequest {

    @Valid
    private GoodsEvaluateAddRequest goodsEvaluateAddRequest;

    @Valid
    private List<GoodsEvaluateImageAddRequest> goodsEvaluateImageAddRequest;

    @Valid
    private StoreEvaluateAddRequest storeEvaluateAddRequestList;

    @Override
    public String checkSensitiveWord() {
        if (goodsEvaluateAddRequest != null && StringUtils.isNotBlank(goodsEvaluateAddRequest.getEvaluateContent())) {
            return goodsEvaluateAddRequest.getEvaluateContent();
        }
        return null;
    }
}
