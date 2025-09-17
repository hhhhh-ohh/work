package com.wanmi.sbc.goods.provider.impl.goodsadditional;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.SpringContextHolder;
import com.wanmi.sbc.goods.api.provider.goodsadditional.GoodsUpdateValidateProvider;
import com.wanmi.sbc.goods.api.request.goodsadditional.GoodsValidatePutOnRequest;
import com.wanmi.sbc.goods.api.response.goodsadditional.GoodsValidatePutOnResponse;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.service.GoodsAdditionalInterface;
import com.wanmi.sbc.goods.info.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.*;

/**
 * @author wur
 * @className PluginGoodsQueryController
 * @description 查询商品插件信息
 * @date 2021/6/8 16:31
 **/
@RestController
public class GoodsUpdateValidateController implements GoodsUpdateValidateProvider {

    @Autowired private GoodsService goodsService;

    /**
     * 商品批量上下架 插件处理
     * @author  wur
     * @date: 2021/6/24 17:08
     * @param request 批量上下架的商品ID
     * @return   返回处理后的数据
     **/
    @Override
    public BaseResponse<GoodsValidatePutOnResponse> validatePutOnStatus(@RequestBody @Valid GoodsValidatePutOnRequest request) {
        GoodsValidatePutOnResponse response = GoodsValidatePutOnResponse.builder().validatePass(Boolean.TRUE).build();
        Goods goods = goodsService.getGoodsById(request.getGoodsId());
        if(Objects.isNull(goods)) {
            return BaseResponse.success(response);
        }
        try{
            GoodsAdditionalInterface goodsAdditional =
                    (GoodsAdditionalInterface)
                            SpringContextHolder.getBean(goods.getPluginType().name());
            if(Objects.isNull(goodsAdditional)) {
                return BaseResponse.success(response);
            }
            response = goodsAdditional.validatePutOnStatus(request);
        } catch (Exception e) {
        }
        return BaseResponse.success(response);
    }
}