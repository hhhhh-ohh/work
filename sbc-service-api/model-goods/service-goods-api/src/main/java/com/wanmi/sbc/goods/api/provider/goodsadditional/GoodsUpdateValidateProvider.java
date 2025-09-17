package com.wanmi.sbc.goods.api.provider.goodsadditional;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.goodsadditional.GoodsValidatePutOnRequest;
import com.wanmi.sbc.goods.api.response.goodsadditional.GoodsValidatePutOnResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * 商品修改校验接口
 * @Author: wur
 * @Date: 2021/6/8 16:21
 */
@FeignClient(value = "${application.goods.name}", contextId = "GoodsUpdateValidateProvider")
public interface GoodsUpdateValidateProvider {

    /**
     * 商品上架操作验证
     * @author  wur
     * @date: 2021/7/7 12:29
     * @param request
     * @return
     **/
    @PostMapping("/goods/${application.goods.version}/updateValidate/validatePutOn")
    BaseResponse<GoodsValidatePutOnResponse> validatePutOnStatus(@RequestBody @Valid GoodsValidatePutOnRequest request);
}
