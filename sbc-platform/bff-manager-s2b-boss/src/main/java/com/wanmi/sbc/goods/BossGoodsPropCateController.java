package com.wanmi.sbc.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsPropCateSortRequest;
import com.wanmi.sbc.goods.api.provider.goodspropcaterel.GoodsPropCateRelProvider;
import com.wanmi.sbc.goods.api.provider.goodspropcaterel.GoodsPropCateRelQueryProvider;
import com.wanmi.sbc.goods.api.request.goodspropcaterel.GoodsPropCateRelListRequest;
import com.wanmi.sbc.goods.api.request.goodspropcaterel.GoodsPropCateSortRequest;
import com.wanmi.sbc.goods.api.response.goodspropcaterel.GoodsPropCateRelListResponse;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author houshuai
 * @date 2021/4/25 18:01
 * @description <p> 类目属性 </p>
 */
@RestController
@Validated
@RequestMapping("/goods")
@Tag(name =  "类目属性服务", description =  "BossGoodsPropCateController")
public class BossGoodsPropCateController {

    @Autowired
    private GoodsPropCateRelQueryProvider goodsPropCateRelQueryProvider;

    @Autowired
    private GoodsPropCateRelProvider goodsPropCateRelProvider;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    /**
     * 分页查询商品属性
     *
     * @param cateId
     * @return
     */
    @Operation(summary = "分页商品属性")
    @GetMapping(value = "/cate/prop-list/{cateId}")
    public BaseResponse<GoodsPropCateRelListResponse> findGoodsPropertyPage(@PathVariable("cateId") Long cateId) {
        GoodsPropCateRelListRequest request = GoodsPropCateRelListRequest.builder()
                .cateId(cateId)
                .build();
        return goodsPropCateRelQueryProvider.findCateProperty(request);
    }

    /**
     * 分页查询商品属性
     *
     * @param request
     * @return
     */
    @Operation(summary = "类目属性拖拽排序")
    @PutMapping(value = "/cate/modify-sort")
    public BaseResponse modifySort(@RequestBody GoodsPropCateSortRequest request) {
        if (CollectionUtils.isEmpty(request.getGoodsPropCateVOList())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        BaseResponse baseResponse = goodsPropCateRelProvider.modifySort(request);
        //同步es
        EsGoodsPropCateSortRequest sortRequest = EsGoodsPropCateSortRequest.builder()
                .goodsPropCateVOList(request.getGoodsPropCateVOList())
                .build();
        esGoodsInfoElasticProvider.modifyPropSort(sortRequest);
        return baseResponse;
    }

}
