package com.wanmi.sbc.goods;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.goods.api.provider.prop.GoodsPropProvider;
import com.wanmi.sbc.goods.api.provider.prop.GoodsPropQueryProvider;
import com.wanmi.sbc.goods.api.request.prop.*;
import com.wanmi.sbc.goods.api.response.prop.GoodsPropAddResponse;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@Validated
@RequestMapping("/goods")
@Tag(name =  "商品属性服务",description = "BossGoodsPropController")
public class BossGoodsPropController {
    @Autowired
    GoodsPropProvider goodsPropProvider;

    @Autowired
    GoodsPropQueryProvider goodsPropQueryProvider;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @MultiSubmit
    @GlobalTransactional
    @Operation(summary = "新增商品属性")
    @RequestMapping(value = "/goodsProp",method = RequestMethod.POST)
    public BaseResponse addGoodsProps(@RequestBody GoodsPropAddRequest goodsPropRequest){
        BaseResponse<GoodsPropAddResponse> baseResponse = goodsPropProvider.add(goodsPropRequest);
        GoodsPropAddResponse goodsPropAddResponse = baseResponse.getContext();
        if (Objects.isNull(goodsPropAddResponse)){
            return BaseResponse.FAILED();
        }
        List<String> goodsIds = goodsPropAddResponse.getStringList();
        if(CollectionUtils.isNotEmpty(goodsIds)){
            esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().goodsIds(goodsIds).build());
        }
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "修改商品属性")
    @RequestMapping(value = "/goodsProp",method = RequestMethod.PUT)
    public BaseResponse editGoodsProps(@RequestBody GoodsPropModifyRequest goodsProp) {
        goodsPropProvider.modify(goodsProp);
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "删除商品属性")
    @Parameter( name = "propId", description = "属性Id", required = true)
    @RequestMapping(value = "/goodsProp/{propId}",method = RequestMethod.DELETE)
    public BaseResponse deleteGoodsProps(@PathVariable Long propId) {
        goodsPropProvider.deleteByPropId(new GoodsPropDeleteByPropIdRequest(propId));
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "修改商品属性下标")
    @RequestMapping(value = "/goodsProp/editIndex",method = RequestMethod.PUT)
    public BaseResponse editIndex(@RequestBody GoodsPropModifyIndexRequest goodsProp) {
        goodsPropProvider.modifyIndex(goodsProp);
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "商品属性排序")
    @RequestMapping(value = "/goodsProp/editSort",method = RequestMethod.PUT)
    public BaseResponse editSort(@RequestBody GoodsPropModifySortRequest goodsPropRequest) {
        goodsPropProvider.modifySort(goodsPropRequest);
        return BaseResponse.SUCCESSFUL();
    }
}
